package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.DatasourceEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatasourceRowMapper implements RowMapper<DatasourceEntity> {
    @Override
    public DatasourceEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var dId = resultSet.getLong("did");
        final var ip = resultSet.getString("ip");
        final var hostname = resultSet.getString("hostname");
        final var port = resultSet.getInt("port");
        final var database = resultSet.getString("database");
        final var tags = resultSet.getString("tags");
        return new DatasourceEntity(dId, ip, hostname, port, database, tags);
    }
}
