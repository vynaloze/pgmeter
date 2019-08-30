package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.DatasourceDao;
import com.vynaloze.pgmeter.dao.StatDao;
import com.vynaloze.pgmeter.dao.model.FlatStat;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.LinearStats;
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
    private final TranslatorV2 translator;

    @Autowired
    public StatServiceImpl(final StatDao statDao, final DatasourceDao datasourceDao, final Mapper mapper, final TranslatorV2 translator) {
        this.statDao = statDao;
        this.datasourceDao = datasourceDao;
        this.mapper = mapper;
        this.translator = translator;
    }

    @Override
    public Stat saveStat(final Stat stat) {
        final var entity = mapper.toEntity(stat.getDatasource());
        final var ip = entity.getIp();
        final var database = entity.getDatabase();
        var ds = datasourceDao.getDatasource(ip, database);
        if (ds.isEmpty()) {
            synchronized (this) {
                ds = datasourceDao.getDatasource(ip, database);
                if (ds.isEmpty()) {
                    datasourceDao.save(entity);
                    ds = datasourceDao.getDatasource(ip, database);
                }
            }
        }
        final var statEntity = mapper.toEntity(stat, ds.get());
        statDao.save(statEntity);

        return new Stat(stat, new Datasource(stat.getDatasource(), ds.get().getId()));
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
    public LinearStats getLinearStats(final TranslateRequest translateRequest) {
        final var entities = statDao.getStats(
                translateRequest.getFilter().getType(),
                translateRequest.getFilter().getTimestampFrom(),
                translateRequest.getFilter().getTimestampTo(),
                translateRequest.getFilter().getDatasourceIds());

        final var flatStats = entities.stream()
                .map(mapper::toStat)
                .map(s -> s.getPayload()
                        .stream()
                        .map(map -> new FlatStat(s.getTimestamp(), s.getDatasource().getId(), s.getId(), map))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return translator.translate(flatStats, translateRequest);
    }
}
