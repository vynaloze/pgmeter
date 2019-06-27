package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.DatasourceDao;
import com.vynaloze.pgmeter.dao.FlatStat;
import com.vynaloze.pgmeter.dao.StatDao;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.LinearStat;
import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {
    private final StatDao statDao;
    private final DatasourceDao datasourceDao;
    private final Mapper mapper;
    private final Translator translator;

    @Autowired
    public StatServiceImpl(final StatDao statDao, final DatasourceDao datasourceDao, final Mapper mapper, final Translator translator) {
        this.statDao = statDao;
        this.datasourceDao = datasourceDao;
        this.mapper = mapper;
        this.translator = translator;
    }

    @Override
    public boolean saveStat(final Stat stat) {
        final var ds = mapper.toEntity(stat.getDatasource());
        var dsId = datasourceDao.getDatasourceId(ds.getIp(), ds.getDatabase());
        if (dsId.isEmpty()) {
            synchronized (this) {
                dsId = datasourceDao.getDatasourceId(ds.getIp(), ds.getDatabase());
                if (dsId.isEmpty()) {
                    datasourceDao.save(ds);
                    dsId = datasourceDao.getDatasourceId(ds.getIp(), ds.getDatabase());
                }
            }
        }
        return statDao.save(mapper.toEntity(stat, dsId.get()));
    }

    @Override
    public List<Datasource> getDatasources(final Long tsFrom, final Long tsTo) {
        return datasourceDao.getDatasources(tsFrom, tsTo).stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stat> getMostRecentStats(final String type) {
        return statDao.getMostRecentStats(type).stream()
                .map(mapper::toStat)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinearStat> getLinearStats(final TranslateRequest translateRequest) {
        return statDao.getStats(
                translateRequest.getFilter().getType(),
                translateRequest.getFilter().getTimestampFrom(),
                translateRequest.getFilter().getTimestampTo())
                .stream()
                .map(entity -> {
                    final var stat = mapper.toStat(entity);
                    final var flatStats = stat.getPayload()
                            .stream()
                            .map(map -> new FlatStat(stat.getTimestamp(), stat.getDatasource().getId(), stat.getId(), map))
                            .collect(Collectors.toList());
                    return translator.translate(flatStats, translateRequest);
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
