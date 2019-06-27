package com.vynaloze.pgmeter.dao.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.dao.entity.DatasourceEntity;
import com.vynaloze.pgmeter.dao.entity.Row;
import com.vynaloze.pgmeter.dao.entity.StatEntity;
import com.vynaloze.pgmeter.dao.entity.Value;
import com.vynaloze.pgmeter.json.Parser;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Converter {
    public static StatEntity toStat(final Stat dto) {
//        return new StatEntity(null, dto.getTimestamp(), toDatasource(dto.getDatasource()), dto.getId());
        return null;
    }

    public static List<Row> toRows(final Stat dto, final Long statId) {
        return dto.getPayload().stream().map(m -> new Row(null, statId)).collect(Collectors.toList());
    }

    public static List<Value> toValues(final Stat dto, final List<Long> rowIds) {
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

    public static Stat toDto(final StatHolder statHolder) {
        final var payload = new ArrayList<Map<String, Object>>();
        for (final var row : statHolder.getRows()) {
            final Map<String, Object> map = statHolder.getValues().stream()
                    .filter(v -> v.getRowId().equals(row.getId()))
                    .collect(Collectors.toMap(Value::getKey, Value::getValue));
            payload.add(map);
        }
        final var stat = statHolder.getStatEntity();
        //return new Stat(stat.getTimestamp(), toDto(stat.getDatasource()), stat.getType(), payload);
        return null;
    }

    public static DatasourceEntity toDatasource(final Datasource datasource) {
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

    public static Datasource toDto(final DatasourceEntity datasourceEntity) {
        final Map<String, String> tags;
        try {
            tags = Parser.reader(Map.class).readValue(datasourceEntity.getTags());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new Datasource(datasourceEntity.getId(), datasourceEntity.getIp(), datasourceEntity.getHostname(), datasourceEntity.getPort(),
                datasourceEntity.getDatabase(), tags);
    }
}
