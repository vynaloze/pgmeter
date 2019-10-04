package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.StatEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatRowMapper implements RowMapper<StatEntity> {
    @Override
    public StatEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var id = resultSet.getLong("id");
        final var type = resultSet.getString("type");
        final var system = resultSet.getBoolean("system");
        final var postgres = resultSet.getBoolean("postgres");
        return new StatEntity(id, type, system, postgres);
    }
}
