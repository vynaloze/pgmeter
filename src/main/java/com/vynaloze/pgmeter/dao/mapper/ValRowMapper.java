package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.ValEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValRowMapper implements RowMapper<ValEntity> {
    @Override
    public ValEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var sId = resultSet.getLong("sid");
        final var key = resultSet.getString("key");
        return new ValEntity(sId, key);
    }
}
