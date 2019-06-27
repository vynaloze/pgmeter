package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.StatEntity;
import org.springframework.jdbc.core.RowMapper;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StatRowMapper implements RowMapper<StatEntity> {
    @Override
    public StatEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var ds = new DatasourceRowMapper().mapRow(resultSet, i);
        final var sId = resultSet.getLong("sid");
        final var timestamp = resultSet.getLong("timestamp");
        final var type = resultSet.getString("type");
        final var inputStream = resultSet.getClob("payload").getAsciiStream();
        final var payload = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        return new StatEntity(sId, timestamp, ds, type, payload);
    }
}
