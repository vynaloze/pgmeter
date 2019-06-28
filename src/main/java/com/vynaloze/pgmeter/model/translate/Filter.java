package com.vynaloze.pgmeter.model.translate;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public class Filter {
    @NonNull
    private final Long timestampFrom;
    @NonNull
    private final Long timestampTo;
    @NonNull
    private final String type;
    @Nullable
    private final List<Long> datasourceIds;

    public Filter(final @NonNull Long timestampFrom, final @NonNull Long timestampTo, final @NonNull String type, final @Nullable List<Long> datasourceIds) {
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.type = type;
        this.datasourceIds = datasourceIds;
    }

    @NonNull
    public Long getTimestampFrom() {
        return timestampFrom;
    }

    @NonNull
    public Long getTimestampTo() {
        return timestampTo;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @Nullable
    public List<Long> getDatasourceIds() {
        return datasourceIds;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filter)) {
            return false;
        }

        final Filter filter = (Filter) o;

        if (!timestampFrom.equals(filter.timestampFrom)) {
            return false;
        }
        if (!timestampTo.equals(filter.timestampTo)) {
            return false;
        }
        return type.equals(filter.type);
    }

    @Override
    public int hashCode() {
        int result = timestampFrom.hashCode();
        result = 31 * result + timestampTo.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
