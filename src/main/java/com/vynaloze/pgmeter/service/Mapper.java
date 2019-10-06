package com.vynaloze.pgmeter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.model.DatasourceEntity;
import com.vynaloze.pgmeter.dao.model.DateEntity;
import com.vynaloze.pgmeter.dao.model.StatEntity;
import com.vynaloze.pgmeter.json.Parser;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;

import java.io.IOException;
import java.util.Map;

class Mapper {
    static DatasourceEntity toDatasourceEntity(final Stat stat) {
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

    static DateEntity toDateEntity(final Stat stat) {
        return new DateEntity(null, stat.getTimestamp());
    }

    static StatEntity toStatEntity(final Stat stat) {
        return new StatEntity(null, stat.getId(),
                stat.getDatasource().getDatabase() == null, stat.getDatasource().getDatabase() != null);
    }


    static Datasource toModelDatasource(final DatasourceEntity entity) {
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
