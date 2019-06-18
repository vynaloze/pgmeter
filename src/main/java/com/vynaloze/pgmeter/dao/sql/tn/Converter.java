package com.vynaloze.pgmeter.dao.sql.tn;

import com.vynaloze.pgmeter.dao.sql.BaseConverter;
import com.vynaloze.pgmeter.dao.sql.tn.entity.BaseStat;
import com.vynaloze.pgmeter.dto.StatDto;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Converter extends BaseConverter {
    static <T extends BaseStat> List<T> toStat(final StatDto dto, final Class clazz) {
        final var list = new ArrayList<T>();
        for (final var payload : dto.getPayload()) {
            try {
                payload.put("id", dto.getId());
                payload.put("timestamp", dto.getTimestamp());
                payload.put("datasource", dto.getDatasource());
                list.add(Parser.reader(clazz).readValue(Parser.writer().writeValueAsString(payload)));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    static <T extends BaseStat> StatDto toDto(final List<T> stats, final String type) {
        final var payload = new ArrayList<Map<String, Object>>();
        for (final var stat : stats) {
            try {
                payload.add(Parser.reader(Map.class).readValue(Parser.writer().writeValueAsString(stat)));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new StatDto(stats.get(0).getTimestamp(), toDto(stats.get(0).getDatasource()), type, payload);
    }

    static String toCamelCase(final String source) {
        var string = source;
        while (string.contains("_")) {
            string = string.replaceFirst("_[a-z]", String.valueOf(Character.toUpperCase(string.charAt(string.indexOf("_") + 1))));
        }
        return string;
    }

    static String toSnakeCase(final String source) {
        return source.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }
}
