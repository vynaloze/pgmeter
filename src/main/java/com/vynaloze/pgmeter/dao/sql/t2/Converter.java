package com.vynaloze.pgmeter.dao.sql.t2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.sql.Datasource;
import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.dto.StatDto;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.Collections;
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
        String tags = "{}";
        try {
            tags = Parser.writer().writeValueAsString(dsDto.getTags());
        } catch (JsonProcessingException e) {
            e.printStackTrace();    //fixme here and 3x below
        }
        final var ds = new Datasource(null, dsDto.getIp(), dsDto.getHostname(), dsDto.getPort(), database, tags);
        String payload = "{}";
        try {
            payload = Parser.writer().writeValueAsString(dto.getPayload());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Stat(null, dto.getTimestamp(), ds, dto.getId(), payload);
    }

    static StatDto toDto(final Stat stat) {
        final var ds = stat.getDatasource();
        Map<String, String> tags = Collections.emptyMap();
        try {
            tags = Parser.reader(Map.class).readValue(ds.getTags());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> payload = Collections.emptyMap();
        try {
            payload = Parser.reader(Map.class).readValue(stat.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
        }
        final var dsDto = new DatasourceDto(ds.getId(), ds.getIp(), ds.getHostname(), ds.getPort(), ds.getDatabase(), tags);
        return new StatDto(stat.getTimestamp(), dsDto, stat.getType(), payload);
    }
}
