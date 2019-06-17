package com.vynaloze.pgmeter.dao.sql.t4;

import com.vynaloze.pgmeter.dao.sql.BaseConverter;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Row;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Stat;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Value;
import com.vynaloze.pgmeter.dto.StatDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Converter extends BaseConverter {
    static Stat toStat(final StatDto dto) {
        return new Stat(null, dto.getTimestamp(), toDatasource(dto.getDatasource()), dto.getId());
    }

    static List<Row> toRows(final StatDto dto, final Long statId) {
        return dto.getPayload().stream().map(m -> new Row(null, statId)).collect(Collectors.toList());
    }

    static List<Value> toValues(final StatDto dto, final List<Long> rowIds) {
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

    static StatDto toDto(final StatHolder statHolder) {
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
}
