package com.vynaloze.pgmeter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.DatasourceDao;
import com.vynaloze.pgmeter.dao.DateDao;
import com.vynaloze.pgmeter.dao.StatDao;
import com.vynaloze.pgmeter.dao.ValDao;
import com.vynaloze.pgmeter.dao.model.DatasourceEntity;
import com.vynaloze.pgmeter.dao.model.DateEntity;
import com.vynaloze.pgmeter.dao.model.FactEntity;
import com.vynaloze.pgmeter.dao.model.StatEntity;
import com.vynaloze.pgmeter.json.Parser;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class Mapper {
    private final DatasourceDao datasourceDao;
    private final DateDao dateDao;
    private final StatDao statDao;
    private final ValDao valDao;

    @Autowired
    public Mapper(final DatasourceDao datasourceDao, final DateDao dateDao, final StatDao statDao, final ValDao valDao) {
        this.datasourceDao = datasourceDao;
        this.dateDao = dateDao;
        this.statDao = statDao;
        this.valDao = valDao;
    }

    DatasourceEntity toDatasourceEntity(final Stat stat) {
        final var datasource = stat.getDatasource();
        final String database;
        if (datasource.getDatabase() == null) {
            database = "#system";
        } else {
            database = datasource.getDatabase();
        }
        final String tags;
        try {
            tags = Parser.writer().writeValueAsString(datasource.getTags());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new DatasourceEntity(null, datasource.getIp(), datasource.getHostname(), datasource.getPort(),
                database, tags);
    }

    DateEntity toDateEntity(final Stat stat) {
        return new DateEntity(null, stat.getTimestamp());
    }

    StatEntity toStatEntity(final Stat stat) {
        return new StatEntity(null, stat.getId(),
                stat.getDatasource().getDatabase() == null, stat.getDatasource().getDatabase() != null);
    }

    Datasource toModelDatasource(final DatasourceEntity entity) {
        final Map<String, String> tags;
        try {
            tags = Parser.reader(Map.class).readValue(entity.getTags());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new Datasource(entity.getId(), entity.getIp(), entity.getHostname(), entity.getPort(),
                entity.getDatabase(), tags);
    }

    List<Stat> toModelStats(final List<FactEntity> facts) {
        final var singlePayloadGroups = facts.stream()
                .collect(Collectors.groupingBy(f -> new SinglePayloadGroup(f.getDatasourceId(), f.getDateId(), f.getStatId())));
        return singlePayloadGroups.values().stream()
                .map(singlePayloadGroup -> {
                    final var timestamp = dateDao.getById(singlePayloadGroup.get(0).getDateId()).orElseThrow().getTimestamp();
                    final var datasource = this.toModelDatasource(datasourceDao.getById(singlePayloadGroup.get(0).getDatasourceId()).orElseThrow());
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

    private static class SinglePayloadGroup {
        private final Long datasourceId;
        private final Long dateId;
        private final Long statId;

        SinglePayloadGroup(final Long datasourceId, final Long dateId, final Long statId) {
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
