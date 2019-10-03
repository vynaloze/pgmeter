package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;

public class DateEntity {
    private final Long id;
    @NotNull
    private final Long timestamp;

    public DateEntity(final Long id, @NotNull final Long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DateEntity that = (DateEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}
