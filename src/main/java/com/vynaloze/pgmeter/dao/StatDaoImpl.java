package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.mapper.StatRowMapper;
import com.vynaloze.pgmeter.dao.model.StatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class StatDaoImpl implements StatDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StatDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final StatEntity statEntity) {
        final var insertQuery = "insert into stats (name, system, postgres) values (:name, :system, :postgres)";
        final var params = new HashMap<String, Object>();
        params.put("name", statEntity.getName());
        params.put("system", statEntity.getSystem());
        params.put("postgres", statEntity.getPostgres());
        jdbcTemplate.update(insertQuery, params);
    }

    @Override
    public Optional<StatEntity> getById(final Long id) {
        final var query = "select id, name, system, postgres from stats where id = :id";
        final var params = new HashMap<String, Object>();
        params.put("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new StatRowMapper())));
    }

    @Override
    public Optional<StatEntity> getByName(final String name) {
        final var query = "select id, name, system, postgres from stats where name = :name";
        final var params = new HashMap<String, Object>();
        params.put("name", name);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new StatRowMapper())));
    }
}
