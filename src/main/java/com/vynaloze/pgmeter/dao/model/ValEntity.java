package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;

public class ValEntity {
    private final Long id;
    @NotNull
    private final String key;

    public ValEntity(final Long id, @NotNull final String key) {
        this.id = id;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ValEntity valEntity = (ValEntity) o;

        if (id != null ? !id.equals(valEntity.id) : valEntity.id != null) return false;
        return key.equals(valEntity.key);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + key.hashCode();
        return result;
    }
}
