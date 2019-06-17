package com.vynaloze.pgmeter.dao.sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.Map;

public class BaseConverter {
    public static Datasource toDatasource(final DatasourceDto datasourceDto) {
        final String database;
        if (datasourceDto.getDatabase() == null) {
            database = "#system";
        } else {
            database = datasourceDto.getDatabase();
        }
        final String tags;
        try {
            tags = Parser.writer().writeValueAsString(datasourceDto.getTags());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Datasource(null, datasourceDto.getIp(), datasourceDto.getHostname(), datasourceDto.getPort(),
                database, tags);
    }

    public static DatasourceDto toDto(final Datasource datasource) {
        final Map<String, String> tags;
        try {
            tags = Parser.reader(Map.class).readValue(datasource.getTags());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new DatasourceDto(datasource.getId(), datasource.getIp(), datasource.getHostname(), datasource.getPort(),
                datasource.getDatabase(), tags);
    }
}
