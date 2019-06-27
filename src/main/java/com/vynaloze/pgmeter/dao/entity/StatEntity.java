package com.vynaloze.pgmeter.dao.entity;

import org.springframework.lang.NonNull;

public class StatEntity {
    @NonNull
    private final Long id;
    @NonNull
    private final Long timestamp;
    @NonNull
    private final Long datasourceId;
    @NonNull
    private final String type;

    // todo here will be CLOB

    public StatEntity(final Long id, final Long timestamp, final Long datasourceId, final String type) {
        this.id = id;
        this.timestamp = timestamp;
        this.datasourceId = datasourceId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getDatasource() {
        return datasourceId;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatEntity)) {
            return false;
        }

        final StatEntity statEntity = (StatEntity) o;

        if (!id.equals(statEntity.id)) {
            return false;
        }
        if (!timestamp.equals(statEntity.timestamp)) {
            return false;
        }
        if (!datasourceId.equals(statEntity.datasourceId)) {
            return false;
        }
        return type.equals(statEntity.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + datasourceId.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
