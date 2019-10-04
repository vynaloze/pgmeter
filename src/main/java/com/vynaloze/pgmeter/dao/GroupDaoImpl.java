package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public class GroupDaoImpl implements GroupDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final GroupEntity groupEntity) {
        final var insertQuery = "insert into groups values ()";
        final var params = new HashMap<String, Object>();
        jdbcTemplate.update(insertQuery, params);
    }
}
