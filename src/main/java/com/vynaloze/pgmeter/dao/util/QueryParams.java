package com.vynaloze.pgmeter.dao.util;

import org.springframework.lang.NonNull;

public class QueryParams {
    @NonNull
    private final String query;
    @NonNull
    private final Object[] params;

    public QueryParams(final String query, final Object[] params) {
        this.query = query;
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public Object[] getParams() {
        return params;
    }
}
