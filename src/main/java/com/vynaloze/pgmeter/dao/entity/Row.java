package com.vynaloze.pgmeter.dao.entity;

import org.springframework.lang.NonNull;

public class Row {
    @NonNull
    private final Long id;
    @NonNull
    private final Long statId;

    public Row(final Long id, final Long statId) {
        this.id = id;
        this.statId = statId;
    }

    public Long getId() {
        return id;
    }

    public Long getStatId() {
        return statId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Row)) {
            return false;
        }

        final Row row = (Row) o;

        if (!id.equals(row.id)) {
            return false;
        }
        return statId.equals(row.statId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + statId.hashCode();
        return result;
    }
}
