package com.vynaloze.pgmeter.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.json.Parser;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class Stat {
    @NotNull
    private final Long timestamp;
    @NotNull
    private final Datasource datasource;
    @NotNull
    private final String id;
    @NotNull
    private final List<Map<String, Object>> payload;

    public Stat(@NotNull final Long timestamp, @NotNull final Datasource datasource, @NotNull final String id, @NotNull final List<Map<String, Object>> payload) {
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.id = id;
        this.payload = payload;
    }

    @NotNull
    public Long getTimestamp() {
        return timestamp;
    }

    @NotNull
    public Datasource getDatasource() {
        return datasource;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public List<Map<String, Object>> getPayload() {
        return payload;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stat)) {
            return false;
        }

        final Stat stat = (Stat) o;

        if (!timestamp.equals(stat.timestamp)) {
            return false;
        }
        if (!datasource.equals(stat.datasource)) {
            return false;
        }
        if (!id.equals(stat.id)) {
            return false;
        }
        return payload.equals(stat.payload);
    }

    @Override
    public int hashCode() {
        int result = timestamp.hashCode();
        result = 31 * result + datasource.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + payload.hashCode();
        return result;
    }

    @Override
    public String toString() {
        try {
            return Parser.writer().writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
