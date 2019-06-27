package com.vynaloze.pgmeter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.model.DatasourceEntity;
import com.vynaloze.pgmeter.dao.model.StatEntity;
import com.vynaloze.pgmeter.json.Parser;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
class Mapper {
    StatEntity toEntity(final Stat stat, final DatasourceEntity datasource) {
        final String payload;
        try {
            payload = Parser.writer().writeValueAsString(stat.getPayload());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new StatEntity(null, stat.getTimestamp(), datasource, stat.getId(), payload);
    }

    Stat toStat(final StatEntity statEntity) {
        final List<Map<String, Object>> payload;
        try {
            payload = Parser.reader(List.class).readValue(statEntity.getPayload());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new Stat(statEntity.getTimestamp(), toModel(statEntity.getDatasource()), statEntity.getType(), payload);
    }

    DatasourceEntity toEntity(final Datasource datasource) {
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

    Datasource toModel(final DatasourceEntity entity) {
        final Map<String, String> tags;
        try {
            tags = Parser.reader(Map.class).readValue(entity.getTags());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new Datasource(entity.getId(), entity.getIp(), entity.getHostname(), entity.getPort(),
                entity.getDatabase(), tags);
    }
}
