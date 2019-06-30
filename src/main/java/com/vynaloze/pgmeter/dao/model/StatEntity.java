package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;

public class StatEntity {
    @NotNull
    private final Long id;
    @NotNull
    private final Long timestamp;
    @NotNull
    private final DatasourceEntity datasource;
    @NotNull
    private final String type;
    @NotNull
    private final String payload;

    public StatEntity(final @NotNull Long id, final @NotNull Long timestamp, final @NotNull DatasourceEntity datasource, final @NotNull String type, final @NotNull String payload) {
        this.id = id;
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.type = type;
        this.payload = payload;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public Long getTimestamp() {
        return timestamp;
    }

    @NotNull
    public DatasourceEntity getDatasource() {
        return datasource;
    }

    @NotNull
    public String getType() {
        return type;
    }

    @NotNull
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
