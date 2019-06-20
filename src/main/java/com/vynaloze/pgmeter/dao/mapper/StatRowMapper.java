package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.entity.Stat;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.util.function.BiFunction;

public class StatRowMapper implements BiFunction<Row, RowMetadata, Stat> {
    @Override
    public Stat apply(final Row row, final RowMetadata rowMetadata) {
        final var ds = new DatasourceRowMapper().apply(row, rowMetadata);
        final var sId = row.get("sid", Long.class);
        final var timestamp = row.get("timestamp", Long.class);
        final var type = row.get("type", String.class);
        final var payload = row.get("payload", String.class);
        return new Stat(sId, timestamp, ds, type, payload);
    }
}
