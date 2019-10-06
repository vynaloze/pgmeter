package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.DatasourceDao;
import com.vynaloze.pgmeter.dao.FactDao;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.FlatStat;
import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.TranslatedStats;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadServiceImpl implements ReadService {
    private final DatasourceDao datasourceDao;
    private final FactDao factDao;
    private final Mapper mapper;
    private final TranslatorV2 translator;

    @Autowired
    public ReadServiceImpl(final DatasourceDao datasourceDao, final FactDao factDao, final Mapper mapper, final TranslatorV2 translator) {
        this.datasourceDao = datasourceDao;
        this.factDao = factDao;
        this.mapper = mapper;
        this.translator = translator;
    }

    @Override
    public List<Datasource> getDatasources(final Long tsFrom, final Long tsTo) {
        return datasourceDao.getByTimestampBetween(tsFrom, tsTo).stream()
                .map(mapper::toModelDatasource)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stat> getMostRecentStats(final String type) {
        final var entities = factDao.getMostRecent(type);
        return mapper.toModelStats(entities);
    }

    @Override
    public List<TranslatedStats> getTranslatedStats(final TranslateRequest translateRequest) {
        final var entities = factDao.get(
                translateRequest.getFilter().getType(),
                translateRequest.getFilter().getTimestampFrom(),
                translateRequest.getFilter().getTimestampTo(),
                translateRequest.getFilter().getDatasourceIds());
        final var stats = mapper.toModelStats(entities);

        final var flatStats = stats.stream()
                .map(s -> s.getPayload()
                        .stream()
                        .map(map -> new FlatStat(s.getTimestamp(), s.getDatasource().getId(), s.getId(), map))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return translateRequest.getParams().stream()
                .map(params -> new TranslatedStats(params, translator.translate(flatStats, params)))
                .collect(Collectors.toList());
    }
}
