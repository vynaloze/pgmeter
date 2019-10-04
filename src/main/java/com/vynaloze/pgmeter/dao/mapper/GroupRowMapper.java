package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.GroupEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<GroupEntity> {
    @Override
    public GroupEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var id = resultSet.getLong("id");
        return new GroupEntity(id);
    }
}
