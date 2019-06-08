package com.vynaloze.pgmeter.dao.sql.t2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.sql.Datasource;
import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.dto.StatDto;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class Converter {
    static Stat toStat(final StatDto dto) {
        final var dsDto = dto.getDatasource();
        final String database;
        if (dsDto.getDatabase() == null) {
            database = "#system";
        } else {
            database = dsDto.getDatabase();
        }
        final String tags;
        try {
            tags = Parser.writer().writeValueAsString(dsDto.getTags());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        final var ds = new Datasource(null, dsDto.getIp(), dsDto.getHostname(), dsDto.getPort(), database, tags);
        final String payload;
        try {
            payload = Parser.writer().writeValueAsString(dto.getPayload());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Stat(null, dto.getTimestamp(), ds, dto.getId(), payload);
    }

    static StatDto toDto(final Stat stat) {
        final var ds = stat.getDatasource();
        final Map<String, String> tags;
        try {
            tags = Parser.reader(Map.class).readValue(ds.getTags());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        final List<Map<String, Object>> payload;
        try {
            payload = Parser.reader(List.class).readValue(stat.getPayload());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        final var dsDto = new DatasourceDto(ds.getId(), ds.getIp(), ds.getHostname(), ds.getPort(), ds.getDatabase(), tags);
        return new StatDto(stat.getTimestamp(), dsDto, stat.getType(), payload);
    }
}
