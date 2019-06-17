package com.vynaloze.pgmeter.dao.sql.t4;

import com.vynaloze.pgmeter.dao.sql.DatasourceDao;
import com.vynaloze.pgmeter.dao.sql.StatDao;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Row;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Stat;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Value;
import com.vynaloze.pgmeter.dao.sql.t4.mapper.RowRowMapper;
import com.vynaloze.pgmeter.dao.sql.t4.mapper.StatRowMapper;
import com.vynaloze.pgmeter.dao.sql.t4.mapper.ValueRowMapper;
import com.vynaloze.pgmeter.dao.sql.t4.util.SerializationUtils;
import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.dto.StatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("t4")
public class StatDaoT4 implements StatDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DatasourceDao datasourceDao;

    @Autowired
    public StatDaoT4(final NamedParameterJdbcTemplate jdbcTemplate, final DatasourceDao datasourceDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.datasourceDao = datasourceDao;
    }

    @Override
// todo - @Transactional if decided to use it
    public void save(final StatDto statDto) {
        final var stat = Converter.toStat(statDto);
        final var dsId = datasourceDao.saveIfNotExist(stat.getDatasource());
        final var id = insertStat(stat, dsId);

        final var rows = Converter.toRows(statDto, id);
        final var rowIds = insertRows(rows);

        final var values = Converter.toValues(statDto, rowIds);
        insertValues(values);
    }

    private Long insertStat(final Stat stat, final Long dsId) {
        final var insertStat = "insert into stats (timestamp, datasource_id, type) values (:timestamp, :datasource_id, :type)";
        final var params = new HashMap<String, Object>();
        params.put("timestamp", stat.getTimestamp());
        params.put("datasource_id", dsId);
        params.put("type", stat.getType());
        final var sqlParameterSource = new MapSqlParameterSource(params);
        final var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertStat, sqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private List<Long> insertRows(final List<Row> rows) {
        final var query = "insert into rows (stat_id) values (?)";
        try (final var connection = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (final var row : rows) {
                preparedStatement.setLong(1, row.getStatId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            final var resultSet = preparedStatement.getGeneratedKeys();
            final var ids = new ArrayList<Long>();
            while (resultSet.next()) {
                ids.add(resultSet.getLong(1));
            }
            return ids;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertValues(final List<Value> values) {
        final var query = "insert into vals (row_id, key, value) values (?,?,?)";
        jdbcTemplate.getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement preparedStatement, final int i) throws SQLException {
                final var value = values.get(i);
                preparedStatement.setLong(1, value.getRowId());
                preparedStatement.setString(2, value.getKey());
                preparedStatement.setObject(3, SerializationUtils.toByteArray(value.getValue()));
            }

            @Override
            public int getBatchSize() {
                return values.size();
            }
        });
    }

    @Override
    public List<StatDto> getStats(final Long tsFrom, final Long tsTo, final String type) {
        final var stats = selectStats(tsFrom, tsTo, type);
        final var rows = selectRows(stats.stream().map(Stat::getId).collect(Collectors.toList()));
        return stats.stream().map(stat -> {
            final var statRows = rows.get(stat.getId());
            final var statVals = selectValues(statRows.stream().map(Row::getId).collect(Collectors.toList()));
            return Converter.toDto(new StatHolder(stat, statRows, statVals));
        }).collect(Collectors.toList());
    }

    private List<Stat> selectStats(final Long tsFrom, final Long tsTo, final String type) {
        final var query = "select d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags, " +
                "s.id as sid, s.timestamp as timestamp, s.datasource_id as datasource_id, s.type as type " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo and s.type = :type";
        final var params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("tsFrom", tsFrom);
        params.put("tsTo", tsTo);
        return jdbcTemplate.query(query, params, new StatRowMapper());
    }

    private Map<Long, List<Row>> selectRows(final List<Long> statIds) {
        final var query = "select * from rows where stat_id in (:ids)";
        final var params = new HashMap<String, Object>();
        params.put("ids", statIds);
        final var list = jdbcTemplate.query(query, params, new RowRowMapper());
        return list.stream().collect(Collectors.groupingBy(Row::getStatId));
    }

    private List<Value> selectValues(final List<Long> rowIds) {
        final var query = "select * from vals where row_id in (:ids)";
        final var params = new HashMap<String, Object>();
        params.put("ids", rowIds);
        return jdbcTemplate.query(query, params, new ValueRowMapper());
    }

    @Override
    public List<DatasourceDto> getDatasources(final Long tsFrom, final Long tsTo) {
        return datasourceDao.getDatasources(tsFrom, tsTo);
    }
}
