package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.entity.Datasource;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.util.function.BiFunction;

public class DatasourceRowMapper implements BiFunction<Row, RowMetadata, Datasource> {
    @Override
    public Datasource apply(final Row row, final RowMetadata rowMetadata) {
        final var dId = row.get("did", Long.class);
        final var ip = row.get("ip", String.class);
        final var hostname = row.get("hostname", String.class);
        final var port = row.get("port", Integer.class);
        final var database = row.get("database", String.class);
        final var tags = row.get("tags", String.class);
        return new Datasource(dId, ip, hostname, port, database, tags);
    }
}