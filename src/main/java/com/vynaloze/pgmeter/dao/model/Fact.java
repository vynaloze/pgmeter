package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class Fact {
    private final Long id;
    @NotNull
    private final DatasourceEntity datasourceEntity;
    @NotNull
    private final DateEntity dateEntity;
    @NotNull
    private final StatEntity statEntity;
    @NotNull
    private final GroupEntity groupEntity;
    @NotNull
    private final ValEntity valEntity;
    @NotNull
    private final byte[] value;

    public Fact(final Long id, @NotNull final DatasourceEntity datasourceEntity, @NotNull final DateEntity dateEntity, @NotNull final StatEntity statEntity, @NotNull final GroupEntity groupEntity, @NotNull final ValEntity valEntity, final @NotNull byte[] value) {
        this.id = id;
        this.datasourceEntity = datasourceEntity;
        this.dateEntity = dateEntity;
        this.statEntity = statEntity;
        this.groupEntity = groupEntity;
        this.valEntity = valEntity;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public DatasourceEntity getDatasourceEntity() {
        return datasourceEntity;
    }

    public DateEntity getDateEntity() {
        return dateEntity;
    }

    public StatEntity getStatEntity() {
        return statEntity;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public ValEntity getValEntity() {
        return valEntity;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Fact that = (Fact) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!datasourceEntity.equals(that.datasourceEntity)) return false;
        if (!dateEntity.equals(that.dateEntity)) return false;
        if (!statEntity.equals(that.statEntity)) return false;
        if (!groupEntity.equals(that.groupEntity)) return false;
        if (!valEntity.equals(that.valEntity)) return false;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + datasourceEntity.hashCode();
        result = 31 * result + dateEntity.hashCode();
        result = 31 * result + statEntity.hashCode();
        result = 31 * result + groupEntity.hashCode();
        result = 31 * result + valEntity.hashCode();
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}
