package com.vynaloze.pgmeter.dao.sql.t2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.sql.BaseConverter;
import com.vynaloze.pgmeter.dto.StatDto;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class Converter extends BaseConverter {
    static Stat toStat(final StatDto dto) {
        final String payload;
        try {
            payload = Parser.writer().writeValueAsString(dto.getPayload());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Stat(null, dto.getTimestamp(), toDatasource(dto.getDatasource()), dto.getId(), payload);
    }

    static StatDto toDto(final Stat stat) {
        final List<Map<String, Object>> payload;
        try {
            payload = Parser.reader(List.class).readValue(stat.getPayload());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new StatDto(stat.getTimestamp(), toDto(stat.getDatasource()), stat.getType(), payload);
    }
}
