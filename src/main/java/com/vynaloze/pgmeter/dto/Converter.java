package com.vynaloze.pgmeter.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.entity.Datasource;
import com.vynaloze.pgmeter.dao.entity.Stat;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Converter {
    public static Stat toEntity(final StatDto dto) {
        final String payload;
        try {
            payload = Parser.writer().writeValueAsString(dto.getPayload());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var ds = toDatasource(dto.getDatasource());
        return new Stat(null, dto.getTimestamp(), ds, dto.getId(), payload);
    }

    public static StatDto toDto(final Stat stat) {
        final List<Map<String, Object>> payload;
        try {
            payload = Parser.reader(List.class).readValue(stat.getPayload());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new StatDto(stat.getTimestamp(), toDto(stat.getDatasource()), stat.getType(), payload);
    }

    public static Datasource toDatasource(final DatasourceDto datasourceDto) {
        final String database;
        if (datasourceDto.getDatabase() == null) {
            database = "#system";
        } else {
            database = datasourceDto.getDatabase();
        }
        String tags = null;
        if (datasourceDto.getTags() != null && !datasourceDto.getTags().isEmpty()) {
            try {
                tags = Parser.writer().writeValueAsString(datasourceDto.getTags());
            } catch (final JsonProcessingException e) {
                throw new RuntimeException(e);
            }
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
