package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.*;
import com.vynaloze.pgmeter.dao.model.FactEntity;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.TranslatedStats;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReadServiceImpl implements ReadService {
    private final DatasourceDao datasourceDao;
    private final DateDao dateDao;
    private final StatDao statDao;
    private final GroupDao groupDao;
    private final ValDao valDao;
    private final FactDao factDao;

    @Autowired
    public ReadServiceImpl(final DatasourceDao datasourceDao, final DateDao dateDao, final StatDao statDao, final GroupDao groupDao, final ValDao valDao, final FactDao factDao) {
        this.datasourceDao = datasourceDao;
        this.dateDao = dateDao;
        this.statDao = statDao;
        this.groupDao = groupDao;
        this.valDao = valDao;
        this.factDao = factDao;
    }

    @Override
    public List<Datasource> getDatasources(final Long tsFrom, final Long tsTo) {
        return datasourceDao.getByTimestampBetween(tsFrom, tsTo).stream()
                .map(Mapper::toModelDatasource)
                .collect(Collectors.toList());
    }

    @Override
    public List<Stat> getMostRecentStats(final String type) {
        final var singlePayloadGroups = factDao.getMostRecent(type).stream()
                .collect(Collectors.groupingBy(f -> new SinglePayloadGroup(f.getDatasourceId(), f.getDateId(), f.getStatId())));
        return singlePayloadGroups.values().stream()
                .map(singlePayloadGroup -> {
                    final var timestamp = dateDao.getById(singlePayloadGroup.get(0).getDateId()).orElseThrow().getTimestamp();
                    final var datasource = Mapper.toModelDatasource(datasourceDao.getById(singlePayloadGroup.get(0).getDatasourceId()).orElseThrow());
                    final var id = statDao.getById(singlePayloadGroup.get(0).getStatId()).orElseThrow().getName();
                    final var payload = singlePayloadGroup.stream()
                            .collect(Collectors.groupingBy(FactEntity::getGroupId))
                            .values().stream()
                            .map(singleGroup -> {
                                final var payloadGroup = new HashMap<String, Object>();
                                singleGroup.forEach(factEntity -> payloadGroup.put(
                                        valDao.getById(factEntity.getValId()).orElseThrow().getKey(),
                                        SerializationUtils.deserialize(factEntity.getValue())
                                ));
                                return (Map<String, Object>) payloadGroup;
                            })
                            .collect(Collectors.toList());
                    return new Stat(timestamp, datasource, id, payload);
                }).collect(Collectors.toList());
    }

    @Override
    public List<TranslatedStats> getTranslatedStats(final TranslateRequest translateRequest) {
        return null;
        //        final var entities = factDao.get(
//                translateRequest.getFilter().getType(),
//                translateRequest.getFilter().getTimestampFrom(),
//                translateRequest.getFilter().getTimestampTo(),
//                translateRequest.getFilter().getDatasourceIds());
//
//        final var flatStats = entities.stream()
//                .map(mapperService::toStat)
//                .map(s -> s.getPayload()
//                        .stream()
//                        .map(map -> new FlatStat(s.getTimestamp(), s.getDatasource().getId(), s.getId(), map))
//                        .collect(Collectors.toList()))
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
//
//        return translateRequest.getParams().stream()
//                .map(params -> new TranslatedStats(params, translator.translate(flatStats, params)))
//                .collect(Collectors.toList());
    }

    private static class SinglePayloadGroup {
        private final Long datasourceId;
        private final Long dateId;
        private final Long statId;

        public SinglePayloadGroup(final Long datasourceId, final Long dateId, final Long statId) {
            this.datasourceId = datasourceId;
            this.dateId = dateId;
            this.statId = statId;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SinglePayloadGroup)) {
                return false;
            }

            final SinglePayloadGroup that = (SinglePayloadGroup) o;

            if (!datasourceId.equals(that.datasourceId)) {
                return false;
            }
            if (!dateId.equals(that.dateId)) {
                return false;
            }
            return statId.equals(that.statId);
        }

        @Override
        public int hashCode() {
            int result = datasourceId.hashCode();
            result = 31 * result + dateId.hashCode();
            result = 31 * result + statId.hashCode();
            return result;
        }
    }
}
