package com.vynaloze.pgmeter.dao.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.entity.Datasource;
import com.vynaloze.pgmeter.dao.entity.Row;
import com.vynaloze.pgmeter.dao.entity.Stat;
import com.vynaloze.pgmeter.dao.entity.Value;
import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.dto.StatDto;
import com.vynaloze.pgmeter.json.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Converter {
    public static Stat toStat(final StatDto dto) {
        return new Stat(null, dto.getTimestamp(), toDatasource(dto.getDatasource()), dto.getId());
    }

    public static List<Row> toRows(final StatDto dto, final Long statId) {
        return dto.getPayload().stream().map(m -> new Row(null, statId)).collect(Collectors.toList());
    }

    public static List<Value> toValues(final StatDto dto, final List<Long> rowIds) {
        final var values = new ArrayList<Value>();
        final var payload = dto.getPayload();
        if (payload.size() != rowIds.size()) {
            throw new RuntimeException("payload size and rowIds size are different");
        }
        for (var i = 0; i < rowIds.size(); i++) {
            for (final var entry : payload.get(i).entrySet()) {
                values.add(new Value(null, rowIds.get(i), entry.getKey(), entry.getValue()));
            }
        }
        return values;
    }

    public static StatDto toDto(final StatHolder statHolder) {
        final var payload = new ArrayList<Map<String, Object>>();
        for (final var row : statHolder.getRows()) {
            final Map<String, Object> map = statHolder.getValues().stream()
                    .filter(v -> v.getRowId().equals(row.getId()))
                    .collect(Collectors.toMap(Value::getKey, Value::getValue));
            payload.add(map);
        }
        final var stat = statHolder.getStat();
        return new StatDto(stat.getTimestamp(), toDto(stat.getDatasource()), stat.getType(), payload);
    }

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
