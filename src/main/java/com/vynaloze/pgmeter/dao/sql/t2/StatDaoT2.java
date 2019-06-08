package com.vynaloze.pgmeter.dao.sql.t2;

import com.vynaloze.pgmeter.dao.sql.StatDao;
import com.vynaloze.pgmeter.dto.StatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository("t2")
public class StatDaoT2 implements StatDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StatDaoT2(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(final StatDto statDto) {
        final var stat = Converter.toStat(statDto);
        try {
            final var insertDs = "insert into datasources (ip, hostname, port, database, tags) values (:ip, :hostname, :port, :database, :tags)";
            final var params = new HashMap<String, Object>();
            params.put("ip", stat.getDatasource().getIp());
            params.put("hostname", stat.getDatasource().getHostname());
            params.put("port", stat.getDatasource().getPort());
            params.put("database", stat.getDatasource().getDatabase());
            params.put("tags", stat.getDatasource().getTags());
            jdbcTemplate.update(insertDs, params);
        } catch (DuplicateKeyException e) {
            // do nothing
        }
        final var dsId = getDatasourceId(stat.getDatasource().getIp(), stat.getDatasource().getDatabase());
        final var insertStat = "insert into stats (timestamp, datasource_id, type, payload) values (:timestamp, :datasource_id, :type, :payload)";
        final var params = new HashMap<String, Object>();
        params.put("timestamp", stat.getTimestamp());
        params.put("datasource_id", dsId);
        params.put("type", stat.getType());
        params.put("payload", stat.getPayload());
        jdbcTemplate.update(insertStat, params);
    }

    @Override
    public List<StatDto> get(final Long tsFrom, final Long tsTo, final String type) {
        final var query = "select d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags, " +
                "s.id as sid, s.timestamp as timestamp, s.datasource_id as datasource_id, s.type as type, s.payload as payload " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo and s.type = :type";
        final var params = new HashMap<String, Object>();
        params.put("tsFrom", tsFrom);
        params.put("tsTo", tsTo);
        params.put("type", type);
        final var list = jdbcTemplate.query(query, params, new StatRowMapper());
        return list.stream().map(Converter::toDto).collect(Collectors.toList());
    }

    private Integer getDatasourceId(final String ip, final String database) {
        final var getDsId = "select id from datasources where ip = :ip and database = :database";
        final var params = new HashMap<String, Object>();
        params.put("ip", ip);
        params.put("database", database);
        return jdbcTemplate.queryForObject(getDsId, params, Integer.class);
    }
}
