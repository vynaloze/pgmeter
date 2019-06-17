package com.vynaloze.pgmeter.dao.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class DatasourceDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DatasourceDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer saveIfNotExist(final Datasource datasource) {
        try {
            return getDatasourceId(datasource.getIp(), datasource.getDatabase());
        } catch (final IncorrectResultSizeDataAccessException e) {
            // means there is no such datasource in the database yet
            synchronized (DatasourceDao.class) {
                // once more, this time synchronized
                try {
                    return getDatasourceId(datasource.getIp(), datasource.getDatabase());
                } catch (final IncorrectResultSizeDataAccessException e2) {
                    final var insertDs = "insert into datasources (ip, hostname, port, database, tags) values (:ip, :hostname, :port, :database, :tags)";
                    final var params = new HashMap<String, Object>();
                    params.put("ip", datasource.getIp());
                    params.put("hostname", datasource.getHostname());
                    params.put("port", datasource.getPort());
                    params.put("database", datasource.getDatabase());
                    params.put("tags", datasource.getTags());
                    jdbcTemplate.update(insertDs, params);
                    return getDatasourceId(datasource.getIp(), datasource.getDatabase());
                }
            }
        }
    }

    private Integer getDatasourceId(final String ip, final String database) {
        final var getDsId = "select id from datasources where ip = :ip and database = :database";
        final var params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("database", database);
        return jdbcTemplate.queryForObject(getDsId, params, Integer.class);
    }
}
