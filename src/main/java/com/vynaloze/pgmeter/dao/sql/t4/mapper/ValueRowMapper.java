package com.vynaloze.pgmeter.dao.sql.t4.mapper;

import com.vynaloze.pgmeter.dao.sql.t4.entity.Value;
import com.vynaloze.pgmeter.dao.sql.t4.util.SerializationUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ValueRowMapper implements RowMapper<Value> {
    @Override
    public Value mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var id = resultSet.getLong("id");
        final var rowId = resultSet.getLong("row_id");
        final var key = resultSet.getString("key");
        final var valueBytes = resultSet.getBytes("value");
        return new Value(id, rowId, key, SerializationUtils.toObject(valueBytes));
    }
}
