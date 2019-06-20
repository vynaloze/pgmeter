package com.vynaloze.pgmeter.dao.util;

import com.vynaloze.pgmeter.dao.entity.Datasource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class QueryParamProvider {
    public QueryParams insertDatasource(final Datasource datasource) {
        final var sb = new StringBuilder("insert into datasources (ip,");
        var values = 1;
        final var params = new LinkedList<>();
        params.add(datasource.getIp());

        if (datasource.getHostname() != null) {
            sb.append("hostname,");
            values++;
            params.add(datasource.getHostname());
        }

        if (datasource.getPort() != null) {
            sb.append("port,");
            values++;
            params.add(datasource.getPort());
        }

        sb.append("database");
        values++;
        params.add(datasource.getDatabase());

        if (datasource.getTags() != null) {
            sb.append(",tags");
            values++;
            params.add(datasource.getTags());
        }

        sb.append(") values (");
        for (var i = 1; i <= values; i++) {
            sb.append("$").append(i).append(",");
        }

        sb.deleteCharAt(sb.length() - 1).append(")");
        return new QueryParams(sb.toString(), params.toArray());
    }
}
