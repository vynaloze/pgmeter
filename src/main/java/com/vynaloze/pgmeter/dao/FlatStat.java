package com.vynaloze.pgmeter.dao;

import org.springframework.lang.NonNull;

import java.util.Map;

public class FlatStat {
    @NonNull
    private final Long timestamp;
    @NonNull
    private final Long datasourceId;
    @NonNull
    private final String type;
    @NonNull
    private final Map<String, Object> values;

    public FlatStat(final @NonNull Long timestamp, final @NonNull Long datasourceId, final @NonNull String type, final @NonNull Map<String, Object> values) {
        this.timestamp = timestamp;
        this.datasourceId = datasourceId;
        this.type = type;
        this.values = values;
    }

    @NonNull
    public Long getTimestamp() {
        return timestamp;
    }

    @NonNull
    public Long getDatasourceId() {
        return datasourceId;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlatStat)) {
            return false;
        }

        final FlatStat flatStat = (FlatStat) o;

        if (!timestamp.equals(flatStat.timestamp)) {
            return false;
        }
        if (!datasourceId.equals(flatStat.datasourceId)) {
            return false;
        }
        if (!type.equals(flatStat.type)) {
            return false;
        }
        return values.equals(flatStat.values);
    }

    @Override
    public int hashCode() {
        int result = timestamp.hashCode();
        result = 31 * result + datasourceId.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + values.hashCode();
        return result;
    }
}
