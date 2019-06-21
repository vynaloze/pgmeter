package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.entity.Stat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatRowMapper implements RowMapper<Stat> {
    @Override
    public Stat mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var ds = new DatasourceRowMapper().mapRow(resultSet, i);
        final var id = resultSet.getLong("sid");
        final var timestamp = resultSet.getLong("timestamp");
        final var type = resultSet.getString("type");
        return new Stat(id, timestamp, ds, type);
    }
}
