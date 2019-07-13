package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.mapper.StatRowMapper;
import com.vynaloze.pgmeter.dao.model.StatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;

@Repository
public class StatDaoImpl implements StatDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StatDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(final StatEntity statEntity) {
        final var insertStat = "insert into stats (timestamp, datasource_id, type, payload) values (:timestamp, :datasource_id, :type, :payload)";
        final var paramSource = new MapSqlParameterSource();
        paramSource.addValue("timestamp", statEntity.getTimestamp(), Types.BIGINT);
        paramSource.addValue("datasource_id", statEntity.getDatasource().getId(), Types.BIGINT);
        paramSource.addValue("type", statEntity.getType(), Types.VARCHAR);
        paramSource.addValue("payload", statEntity.getPayload(), Types.CLOB);
        jdbcTemplate.update(insertStat, paramSource);
    }

    @Override
    public List<StatEntity> getMostRecentStats(final String type) {
        final var query = "select d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags, " +
                "s.id as sid, s.timestamp as timestamp, s.datasource_id as datasource_id, s.type as type, s.payload as payload " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.type = :type and s.timestamp = (select max(s1.timestamp) from stats s1 where s1.type=s.type and s1.datasource_id=s.datasource_id)";
        final var params = new HashMap<String, Object>();
        params.put("type", type);
        return jdbcTemplate.query(query, params, new StatRowMapper());
    }

    @Override
    public List<StatEntity> getStats(final String type, final Long tsFrom, final Long tsTo, final List<Long> datasourceIds) {
        final var query = "select d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags, " +
                "s.id as sid, s.timestamp as timestamp, s.datasource_id as datasource_id, s.type as type, s.payload as payload " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo and s.type = :type and s.datasource_id in (:datasources)";
        final var params = new HashMap<String, Object>();
        params.put("tsFrom", tsFrom);
        params.put("tsTo", tsTo);
        params.put("type", type);
        params.put("datasources", datasourceIds);
        return jdbcTemplate.query(query, params, new StatRowMapper());
    }
}
