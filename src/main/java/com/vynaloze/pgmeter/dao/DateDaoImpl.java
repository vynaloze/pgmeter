package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.mapper.DateRowMapper;
import com.vynaloze.pgmeter.dao.model.DateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class DateDaoImpl implements DateDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DateDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final DateEntity dateEntity) {
        final var insertQuery = "insert into dates (timestamp) values (:timestamp)";
        final var params = new HashMap<String, Object>();
        params.put("timestamp", dateEntity.getTimestamp());
        jdbcTemplate.update(insertQuery, params);
    }

    @Override
    public Optional<DateEntity> getById(final Long id) {
        final var query = "select id, timestamp from dates where id = :id";
        final var params = new HashMap<String, Object>();
        params.put("id", id);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new DateRowMapper())));
    }

    @Override
    public Optional<DateEntity> getByTimestamp(final Long timestamp) {
        final var query = "select id, timestamp from dates where timestamp = :timestamp";
        final var params = new HashMap<String, Object>();
        params.put("timestamp", timestamp);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new DateRowMapper())));
    }
}
