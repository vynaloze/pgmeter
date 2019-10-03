package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class FactEntity {
    private final Long id;
    @NotNull
    private final Long datasourceId;
    @NotNull
    private final Long dateId;
    @NotNull
    private final Long statId;
    @NotNull
    private final Long groupId;
    @NotNull
    private final Long valId;
    @NotNull
    private final byte[] value;

    public FactEntity(final Long id, @NotNull final Long datasourceId, @NotNull final Long dateId, @NotNull final Long statId, @NotNull final Long groupId, @NotNull final Long valId, @NotNull final byte[] value) {
        this.id = id;
        this.datasourceId = datasourceId;
        this.dateId = dateId;
        this.statId = statId;
        this.groupId = groupId;
        this.valId = valId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public Long getDateId() {
        return dateId;
    }

    public Long getStatId() {
        return statId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getValId() {
        return valId;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final FactEntity that = (FactEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!datasourceId.equals(that.datasourceId)) return false;
        if (!dateId.equals(that.dateId)) return false;
        if (!statId.equals(that.statId)) return false;
        if (!groupId.equals(that.groupId)) return false;
        if (!valId.equals(that.valId)) return false;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + datasourceId.hashCode();
        result = 31 * result + dateId.hashCode();
        result = 31 * result + statId.hashCode();
        result = 31 * result + groupId.hashCode();
        result = 31 * result + valId.hashCode();
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}
