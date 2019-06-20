package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.entity.Datasource;
import com.vynaloze.pgmeter.dao.entity.Stat;
import com.vynaloze.pgmeter.dao.mapper.DatasourceRowMapper;
import com.vynaloze.pgmeter.dao.mapper.StatRowMapper;
import com.vynaloze.pgmeter.dao.util.QueryParamProvider;
import io.r2dbc.client.R2dbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class StatDao {
    private final R2dbc client;
    private final QueryParamProvider queryParamProvider;

    @Autowired
    public StatDao(final R2dbc client, final QueryParamProvider queryParamProvider) {
        this.client = client;
        this.queryParamProvider = queryParamProvider;
    }

    public Mono<Void> save(final Stat stat) {
        return Mono.just(stat)
                .zipWith(saveDatasourceIfNotExist(stat.getDatasource()))
                .flatMap(tuple -> saveStat(tuple.getT1(), tuple.getT2()));
    }

    public Flux<Stat> getStats(final Long tsFrom, final Long tsTo, final String type) {
        final var query = "select d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags, " +
                "s.id as sid, s.timestamp as timestamp, s.datasource_id as datasource_id, s.type as type, s.payload as payload " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > $1 and s.timestamp <= $2 and s.type = $3";
        return client.inTransaction(handle ->
                handle.select(query, tsFrom, tsTo, type)
                        .mapResult(result -> result.map(new StatRowMapper()))
        );
    }

    public Flux<Datasource> getDatasources(final Long tsFrom, final Long tsTo) {
        final var query = "select distinct d.id as did, d.ip as ip, d.hostname as hostname, d.port as port, d.database as database, d.tags as tags " +
                "from stats s join datasources d on s.datasource_id = d.id " +
                "where s.timestamp > $1 and s.timestamp <= $2";
        return client.inTransaction(handle ->
                handle.select(query, tsFrom, tsTo)
                        .mapResult(result -> result.map(new DatasourceRowMapper()))
        );
    }

    private Mono<Long> saveDatasourceIfNotExist(final Datasource datasource) {
        return getDatasourceId(datasource.getIp(), datasource.getDatabase())
                .switchIfEmpty(
                        saveDatasource(datasource)
                                .then(getDatasourceId(datasource.getIp(), datasource.getDatabase())));
    }

    private Mono<Void> saveDatasource(final Datasource datasource) {
        final var qp = queryParamProvider.insertDatasource(datasource);
        return client.inTransaction(handle ->
                handle.execute(qp.getQuery(), qp.getParams())
        ).then();
    }

    private Mono<Void> saveStat(final Stat stat, final Long datasourceId) {
        return client.inTransaction(handle ->
                handle.execute("insert into stats (timestamp, datasource_id, type, payload) values ($1,$2,$3,$4)",
                        stat.getTimestamp(), datasourceId, stat.getType(), stat.getPayload())
        ).then();
    }

    private Mono<Long> getDatasourceId(final String ip, final String database) {
        return client.inTransaction(handle ->
                handle.select("select id from datasources where ip = $1 and database = $2", ip, database)
                        .mapResult(result -> result.map((row, rowMetadata) -> row.get("id", Long.class)))
        ).singleOrEmpty();
    }
}
