package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.DateEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DateRowMapper implements RowMapper<DateEntity> {
    @Override
    public DateEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var id = resultSet.getLong("id");
        final var ts = resultSet.getLong("timestamp");
        return new DateEntity(id, ts);
    }
}
