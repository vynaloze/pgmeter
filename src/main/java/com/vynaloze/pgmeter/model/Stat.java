package com.vynaloze.pgmeter.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.json.Parser;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

public class Stat {
    @NonNull
    private final Long timestamp;
    @NonNull
    private final Datasource datasource;
    @NonNull
    private final String id;
    @NonNull
    private final List<Map<String, Object>> payload;

    public Stat(@NonNull final Long timestamp, @NonNull final Datasource datasource, @NonNull final String id, @NonNull final List<Map<String, Object>> payload) {
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.id = id;
        this.payload = payload;
    }

    @NonNull
    public Long getTimestamp() {
        return timestamp;
    }

    @NonNull
    public Datasource getDatasource() {
        return datasource;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
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
