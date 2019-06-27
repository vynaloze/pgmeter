//package com.vynaloze.pgmeter.dao;
//
//import com.vynaloze.pgmeter.dao.entity.Row;
//import com.vynaloze.pgmeter.dao.entity.Value;
//import com.vynaloze.pgmeter.dao.mapper.DatasourceRowMapper;
//import com.vynaloze.pgmeter.dao.mapper.RowRowMapper;
//import com.vynaloze.pgmeter.dao.mapper.StatRowMapper;
//import com.vynaloze.pgmeter.dao.mapper.ValueRowMapper;
//import com.vynaloze.pgmeter.dao.util.Converter;
//import com.vynaloze.pgmeter.dao.util.SerializationUtils;
//import com.vynaloze.pgmeter.dao.util.StatHolder;
//import com.vynaloze.pgmeter.model.DatasourceEntity;
//import com.vynaloze.pgmeter.model.StatEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.IncorrectResultSizeDataAccessException;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Repository
//public class StatDaoImplOld implements StatDao {
//    private final NamedParameterJdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public StatDaoImplOld(final NamedParameterJdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//// todo - @Transactional if decided to use it
//    public void save(final StatEntity statDto) {
//        final var stat = Converter.toStat(statDto);
//        final var dsId = saveIfNotExist(stat.getDatasource());
//        final var id = insertStat(stat, dsId);
//
//        final var rows = Converter.toRows(statDto, id);
//        final var rowIds = insertRows(rows);
//
//        final var values = Converter.toValues(statDto, rowIds);
//        insertValues(values);
//    }
//
//    private Long insertStat(final com.vynaloze.pgmeter.dao.entity.StatEntity stat, final Long dsId) {
//        final var insertStat = "insert into stats (timestamp, datasource_id, type) values (:timestamp, :datasource_id, :type)";
//        final var params = new HashMap<String, Object>();
//        params.put("timestamp", stat.getTimestamp());
//        params.put("datasource_id", dsId);
//        params.put("type", stat.getType());
//        final var sqlParameterSource = new MapSqlParameterSource(params);
//        final var keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(insertStat, sqlParameterSource, keyHolder);
//        return keyHolder.getKey().longValue();
//    }
//
//    private List<Long> insertRows(final List<Row> rows) {
//        final var query = "insert into rows (stat_id) values (?)";
//        try (final var connection = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
//             final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            for (final var row : rows) {
//                preparedStatement.setLong(1, row.getStatId());
//                preparedStatement.addBatch();
//            }
//            preparedStatement.executeBatch();
//            final var resultSet = preparedStatement.getGeneratedKeys();
//            final var ids = new ArrayList<Long>();
//            while (resultSet.next()) {
//                ids.add(resultSet.getLong(1));
//            }
//            return ids;
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void insertValues(final List<Value> values) {
//        final var query = "insert into vals (row_id, key, value) values (?,?,?)";
//        jdbcTemplate.getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(final PreparedStatement preparedStatement, final int i) throws SQLException {
//                final var value = values.get(i);
//                preparedStatement.setLong(1, value.getRowId());
//                preparedStatement.setString(2, value.getKey());
//                preparedStatement.setObject(3, SerializationUtils.toByteArray(value.getValue()));
//            }
//
//            @Override
//            public int getBatchSize() {
//                return values.size();
//            }
//        });
//    }
//
//    @Override
//    public List<StatEntity> getStats(final Long tsFrom, final Long tsTo, final String type) {
//        final var stats = selectStats(tsFrom, tsTo, type);
//        final var rows = selectRows(stats.stream().map(com.vynaloze.pgmeter.dao.entity.StatEntity::getId).collect(Collectors.toList()));
//        return stats.stream().map(stat -> {
//            final var statRows = rows.get(stat.getId());
//            final var statVals = selectValues(statRows.stream().map(Row::getId).collect(Collectors.toList()));
//            return Converter.toDto(new StatHolder(stat, statRows, statVals));
//        }).collect(Collectors.toList());
//    }
//
//    private List<com.vynaloze.pgmeter.dao.entity.StatEntity> selectStats(final Long tsFrom, final Long tsTo, final String type) {
//        final var query = "select d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags, " +
//                "s.id as sid, s.timestamp as timestamp, s.datasource_id as datasource_id, s.type as type " +
//                "from stats s join datasources d on s.datasource_id = d.id " +
//                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo and s.type = :type";
//        final var params = new HashMap<String, Object>();
//        params.put("type", type);
//        params.put("tsFrom", tsFrom);
//        params.put("tsTo", tsTo);
//        return jdbcTemplate.query(query, params, new StatRowMapper());
//    }
//
//    private Map<Long, List<Row>> selectRows(final List<Long> statIds) {
//        final var query = "select * from rows where stat_id in (:ids)";
//        final var params = new HashMap<String, Object>();
//        params.put("ids", statIds);
//        final var list = jdbcTemplate.query(query, params, new RowRowMapper());
//        return list.stream().collect(Collectors.groupingBy(Row::getStatId));
//    }
//
//    private List<Value> selectValues(final List<Long> rowIds) {
//        final var query = "select * from vals where row_id in (:ids)";
//        final var params = new HashMap<String, Object>();
//        params.put("ids", rowIds);
//        return jdbcTemplate.query(query, params, new ValueRowMapper());
//    }
//
//    public Long saveIfNotExist(final com.vynaloze.pgmeter.dao.entity.DatasourceEntity datasource) {
//        try {
//            return getDatasourceId(datasource.getIp(), datasource.getDatabase());
//        } catch (final IncorrectResultSizeDataAccessException e) {
//            // means there is no such datasource in the database yet
//            synchronized (DatasourceDao.class) {
//                // once more, this time synchronized
//                try {
//                    return getDatasourceId(datasource.getIp(), datasource.getDatabase());
//                } catch (final IncorrectResultSizeDataAccessException e2) {
//                    final var insertDs = "insert into datasources (ip, hostname, port, database, tags) values (:ip, :hostname, :port, :database, :tags)";
//                    final var params = new HashMap<String, Object>();
//                    params.put("ip", datasource.getIp());
//                    params.put("hostname", datasource.getHostname());
//                    params.put("port", datasource.getPort());
//                    params.put("database", datasource.getDatabase());
//                    params.put("tags", datasource.getTags());
//                    jdbcTemplate.update(insertDs, params);
//                    return getDatasourceId(datasource.getIp(), datasource.getDatabase());
//                }
//            }
//        }
//    }
//
//    private Long getDatasourceId(final String ip, final String database) {
//        final var getDsId = "select id from datasources where ip = :ip and database = :database";
//        final var params = new HashMap<String, Object>();
//        params.put("ip", ip);
//        params.put("database", database);
//        return jdbcTemplate.queryForObject(getDsId, params, Long.class);
//    }
//
//    @Override
//    public List<DatasourceEntity> getDatasources(final Long tsFrom, final Long tsTo) {
//        final var query = "select distinct d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags " +
//                "from stats s join datasources d on s.datasource_id = d.id " +
//                "where s.timestamp > :tsFrom and s.timestamp <= :tsTo";
//        final var params = new HashMap<String, Object>();
//        params.put("tsFrom", tsFrom);
//        params.put("tsTo", tsTo);
//        final var list = jdbcTemplate.query(query, params, new DatasourceRowMapper());
//        return list.stream().map(Converter::toDto).collect(Collectors.toList());
//    }
//}
