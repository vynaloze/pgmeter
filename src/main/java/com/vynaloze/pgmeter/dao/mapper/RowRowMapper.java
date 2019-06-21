package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.entity.Row;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowRowMapper implements RowMapper<Row> {
    @Override
    public Row mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var id = resultSet.getLong("id");
        final var statId = resultSet.getLong("stat_id");
        return new Row(id, statId);
    }
}
