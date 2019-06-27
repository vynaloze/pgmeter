package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.mapper.DatasourceRowMapper;
import com.vynaloze.pgmeter.dao.model.DatasourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class DatasourceDaoImpl implements DatasourceDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DatasourceDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(final DatasourceEntity datasource) {
        final var insertDs = "insert into datasources (ip, hostname, port, database, tags) values (:ip, :hostname, :port, :database, :tags)";
        final var params = new HashMap<String, Object>();
        params.put("ip", datasource.getIp());
        params.put("hostname", datasource.getHostname());
        params.put("port", datasource.getPort());
        params.put("database", datasource.getDatabase());
        params.put("tags", datasource.getTags());
        jdbcTemplate.update(insertDs, params);
    }

    @Override
    public Optional<DatasourceEntity> getDatasource(final String ip, final String database) {
        final var query = "select id as did, ip, hostname, port, database, tags " +
                "from datasources where ip = :ip and database = :database";
        final var params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("database", database);
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(query, params, new DatasourceRowMapper())));
    }

    @Override
    public List<DatasourceEntity> getDatasources(final Long tsFrom, final Long tsTo) {
        final var query = "select distinct d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo";
        final var params = new HashMap<String, Object>();
        params.put("tsFrom", tsFrom);
        params.put("tsTo", tsTo);
        return jdbcTemplate.query(query, params, new DatasourceRowMapper());
    }
}
