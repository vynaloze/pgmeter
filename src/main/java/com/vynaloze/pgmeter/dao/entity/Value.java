package com.vynaloze.pgmeter.dao.entity;

import org.springframework.lang.NonNull;

public class Value {
    @NonNull
    private final Long id;
    @NonNull
    private final Long rowId;
    @NonNull
    private final String key;
    @NonNull
    private final Object value;

    public Value(final Long id, final Long rowId, final String key, final Object value) {
        this.id = id;
        this.rowId = rowId;
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getRowId() {
        return rowId;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Value)) {
            return false;
        }

        final Value value1 = (Value) o;

        if (!id.equals(value1.id)) {
            return false;
        }
        if (!rowId.equals(value1.rowId)) {
            return false;
        }
        if (!key.equals(value1.key)) {
            return false;
        }
        return value.equals(value1.value);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + rowId.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
