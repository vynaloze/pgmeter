package com.vynaloze.pgmeter.dao.sql.t2;

import com.vynaloze.pgmeter.dao.sql.DatasourceRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatRowMapper implements RowMapper<Stat> {
    @Override
    public Stat mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var ds = new DatasourceRowMapper().mapRow(resultSet, i);
        final var sId = resultSet.getLong("sid");
        final var timestamp = resultSet.getLong("timestamp");
        final var type = resultSet.getString("type");
        final var payload = resultSet.getString("payload");
        return new Stat(sId, timestamp, ds, type, payload);
    }
}
