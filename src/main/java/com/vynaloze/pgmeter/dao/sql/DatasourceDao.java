package com.vynaloze.pgmeter.dao.sql;

import com.vynaloze.pgmeter.dto.DatasourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DatasourceDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DatasourceDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveIfNotExist(final Datasource datasource) {
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

    private Long getDatasourceId(final String ip, final String database) {
        final var getDsId = "select id from datasources where ip = :ip and database = :database";
        final var params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("database", database);
        return jdbcTemplate.queryForObject(getDsId, params, Long.class);
    }

    public List<DatasourceDto> getDatasources(final Long tsFrom, final Long tsTo) {
        final var query = "select distinct d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo";
        final var params = new HashMap<String, Object>();
        params.put("tsFrom", tsFrom);
        params.put("tsTo", tsTo);
        final var list = jdbcTemplate.query(query, params, new DatasourceRowMapper());
        return list.stream().map(BaseConverter::toDto).collect(Collectors.toList());
    }
}
