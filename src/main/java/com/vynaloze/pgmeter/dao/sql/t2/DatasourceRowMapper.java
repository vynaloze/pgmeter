package com.vynaloze.pgmeter.dao.sql.t2;

import com.vynaloze.pgmeter.dao.sql.Datasource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatasourceRowMapper implements RowMapper<Datasource> {
    @Override
    public Datasource mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var dId = resultSet.getLong("did");
        final var ip = resultSet.getString("ip");
        final var hostname = resultSet.getString("hostname");
        final var port = resultSet.getInt("port");
        final var database = resultSet.getString("database");
        final var tags = resultSet.getString("tags");
        return new Datasource(dId, ip, hostname, port, database, tags);
    }
}
