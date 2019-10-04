package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;

public class StatEntity {
    private final Long id;
    @NotNull
    private final String type;
    @NotNull
    private final Boolean system;
    @NotNull
    private final Boolean postgres;

    public StatEntity(final Long id, @NotNull final String type, @NotNull final Boolean system, @NotNull final Boolean postgres) {
        this.id = id;
        this.type = type;
        this.system = system;
        this.postgres = postgres;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Boolean getSystem() {
        return system;
    }

    public Boolean getPostgres() {
        return postgres;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final StatEntity that = (StatEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!type.equals(that.type)) return false;
        if (!system.equals(that.system)) return false;
        return postgres.equals(that.postgres);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + type.hashCode();
        result = 31 * result + system.hashCode();
        result = 31 * result + postgres.hashCode();
        return result;
    }
}
