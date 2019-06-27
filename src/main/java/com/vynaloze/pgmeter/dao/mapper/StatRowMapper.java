package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.entity.StatEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatRowMapper implements RowMapper<StatEntity> {
    @Override
    public StatEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var ds = new DatasourceRowMapper().mapRow(resultSet, i);
        final var id = resultSet.getLong("sid");
        final var timestamp = resultSet.getLong("timestamp");
        final var type = resultSet.getString("type");
//        return new StatEntity(id, timestamp, ds, type);
        return null;
    }
}
