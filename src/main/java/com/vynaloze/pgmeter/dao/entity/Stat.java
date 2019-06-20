package com.vynaloze.pgmeter.dao.entity;

import org.springframework.lang.NonNull;

public class Stat {
    @NonNull
    private final Long id;
    @NonNull
    private final Long timestamp;
    @NonNull
    private final Datasource datasource;
    @NonNull
    private final String type;
    @NonNull
    private final String payload;

    public Stat(@NonNull final Long id, @NonNull final Long timestamp, @NonNull final Datasource datasource,
                @NonNull final String type, @NonNull final String payload) {
        this.id = id;
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.type = type;
        this.payload = payload;
    }

    @NonNull
    public Long getId() {
        return id;
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
    public String getType() {
        return type;
    }

    @NonNull
    public String getPayload() {
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

        if (!id.equals(stat.id)) {
            return false;
        }
        if (!timestamp.equals(stat.timestamp)) {
            return false;
        }
        if (!datasource.equals(stat.datasource)) {
            return false;
        }
        if (!type.equals(stat.type)) {
            return false;
        }
        return payload.equals(stat.payload);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + datasource.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + payload.hashCode();
        return result;
    }
}
