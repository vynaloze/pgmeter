package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class FlatStat {
    @NotNull
    private final Long timestamp;
    @NotNull
    private final Long datasourceId;
    @NotNull
    private final String type;
    @NotNull
    private final Map<String, Object> values;

    public FlatStat(final @NotNull Long timestamp, final @NotNull Long datasourceId, final @NotNull String type, final @NotNull Map<String, Object> values) {
        this.timestamp = timestamp;
        this.datasourceId = datasourceId;
        this.type = type;
        this.values = values;
    }

    @NotNull
    public Long getTimestamp() {
        return timestamp;
    }

    @NotNull
    public Long getDatasourceId() {
        return datasourceId;
    }

    @NotNull
    public String getType() {
        return type;
    }

    @NotNull
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
