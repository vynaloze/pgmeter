package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.mapper.ValRowMapper;
import com.vynaloze.pgmeter.dao.model.ValEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class ValDaoImpl implements ValDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ValDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final ValEntity valEntity) {
        final var insertQuery = "insert into vals (key) values (:key)";
        final var params = new HashMap<String, Object>();
        params.put("key", valEntity.getKey());
        jdbcTemplate.update(insertQuery, params);
    }

    @Override
    public Optional<ValEntity> getById(final Long id) {
        final var query = "select id, key from vals where id = :id";
        final var params = new HashMap<String, Object>();
        params.put("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new ValRowMapper())));
    }

    @Override
    public Optional<ValEntity> getByKey(final String key) {
        final var query = "select id, key from vals where key = :key";
        final var params = new HashMap<String, Object>();
        params.put("key", key);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new ValRowMapper())));
    }
}
