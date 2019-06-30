package com.vynaloze.pgmeter.model.translate;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Filter {
    @NotNull
    private final Long timestampFrom;
    @NotNull
    private final Long timestampTo;
    @NotNull
    private final String type;

    private final List<Long> datasourceIds;

    public Filter(final @NotNull Long timestampFrom, final @NotNull Long timestampTo, final @NotNull String type, final List<Long> datasourceIds) {
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.type = type;
        this.datasourceIds = datasourceIds;
    }

    @NotNull
    public Long getTimestampFrom() {
        return timestampFrom;
    }

    @NotNull
    public Long getTimestampTo() {
        return timestampTo;
    }

    @NotNull
    public String getType() {
        return type;
    }


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
