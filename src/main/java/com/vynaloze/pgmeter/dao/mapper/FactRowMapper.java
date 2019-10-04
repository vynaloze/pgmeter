package com.vynaloze.pgmeter.dao.mapper;

import com.vynaloze.pgmeter.dao.model.FactEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactRowMapper implements RowMapper<FactEntity> {
    @Override
    public FactEntity mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final var id = resultSet.getLong("id");
        final var dsid = resultSet.getLong("datasource_id");
        final var did = resultSet.getLong("date_id");
        final var sid = resultSet.getLong("stat_id");
        final var gid = resultSet.getLong("group_id");
        final var vid = resultSet.getLong("val_id");
        final var value = resultSet.getBytes("value");
        return new FactEntity(id, dsid, did, sid, gid, vid, value);
    }
}
