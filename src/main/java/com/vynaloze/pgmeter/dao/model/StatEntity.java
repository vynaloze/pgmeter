package com.vynaloze.pgmeter.dao.model;

import org.springframework.lang.NonNull;

public class StatEntity {
    @NonNull
    private final Long id;
    @NonNull
    private final Long timestamp;
    @NonNull
    private final DatasourceEntity datasource;
    @NonNull
    private final String type;
    @NonNull
    private final String payload;

    public StatEntity(final @NonNull Long id, final @NonNull Long timestamp, final @NonNull DatasourceEntity datasource, final @NonNull String type, final @NonNull String payload) {
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
    public DatasourceEntity getDatasource() {
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
        if (!(o instanceof StatEntity)) {
            return false;
        }

        final StatEntity that = (StatEntity) o;

        if (!id.equals(that.id)) {
            return false;
        }
        if (!timestamp.equals(that.timestamp)) {
            return false;
        }
        if (!datasource.equals(that.datasource)) {
            return false;
        }
        if (!type.equals(that.type)) {
            return false;
        }
        return payload.equals(that.payload);
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
