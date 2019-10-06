package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.mapper.FactRowMapper;
import com.vynaloze.pgmeter.dao.model.FactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;

@Repository
public class FactDaoImpl implements FactDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public FactDaoImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(final FactEntity factEntity) {
        final var insertStat = "insert into facts (datasource_id, date_id, stat_id, group_id, val_id, value) " +
                "values (:datasource_id, :date_id, :stat_id, :group_id, :val_id, :value)";
        final var paramSource = new MapSqlParameterSource();
        paramSource.addValue("datasource_id", factEntity.getDatasourceId(), Types.BIGINT);
        paramSource.addValue("date_id", factEntity.getDateId(), Types.BIGINT);
        paramSource.addValue("stat_id", factEntity.getStatId(), Types.BIGINT);
        paramSource.addValue("group_id", factEntity.getGroupId(), Types.BIGINT);
        paramSource.addValue("val_id", factEntity.getValId(), Types.BIGINT);
        paramSource.addValue("value", factEntity.getValue(), Types.BINARY);
        jdbcTemplate.update(insertStat, paramSource);
    }

    @Override
    public List<FactEntity> getMostRecent(final String type) {
        final var query = "select f.id, f.datasource_id, f.date_id, f.stat_id, f.group_id, f.val_id, f.value " +
                "from ( " +
                "         select d.id as date_id, m.datasource_id, m.stat_id " +
                "         from ( " +
                "                  select max(d.timestamp) as ts, f1.datasource_id, s.id as stat_id " +
                "                  from facts f1 " +
                "                           join stats s on f1.stat_id = s.id " +
                "                           join dates d on f1.date_id = d.id " +
                "                  where s.name = :name " +
                "                  group by f1.datasource_id, s.id " +
                "              ) m " +
                "                  join dates d on d.timestamp = m.ts " +
                "     ) i " +
                "join facts f on f.date_id = i.date_id and f.datasource_id = i.datasource_id and f.stat_id = i.stat_id";
        final var params = new HashMap<String, Object>();
        params.put("name", type);
        return jdbcTemplate.query(query, params, new FactRowMapper());
    }

    @Override
    public List<FactEntity> get(final String type, final Long tsFrom, final Long tsTo, final List<Long> datasourceIds) {
        final var query = "select f.id, f.datasource_id, f.date_id, f.stat_id, f.group_id, f.val_id, f.value from facts f " +
                "join stats s on f.stat_id = s.id " +
                "join dates d on f.date_id = d.id " +
                "where d.timestamp > :tsFrom and d.timestamp <= :tsTo and s.name = :name and f.datasource_id in (:datasources)";
        final var params = new HashMap<String, Object>();
        params.put("tsFrom", tsFrom);
        params.put("tsTo", tsTo);
        params.put("name", type);
        params.put("datasources", datasourceIds);
        return jdbcTemplate.query(query, params, new FactRowMapper());
    }
}
