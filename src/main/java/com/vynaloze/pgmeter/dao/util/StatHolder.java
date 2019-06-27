package com.vynaloze.pgmeter.dao.util;

import com.vynaloze.pgmeter.dao.entity.Row;
import com.vynaloze.pgmeter.dao.entity.StatEntity;
import com.vynaloze.pgmeter.dao.entity.Value;
import org.springframework.lang.NonNull;

import java.util.List;

public class StatHolder {
    @NonNull
    private final StatEntity statEntity;
    @NonNull
    private final List<Row> rows;
    @NonNull
    private final List<Value> values;

    public StatHolder(final StatEntity statEntity, final List<Row> rows, final List<Value> values) {
        this.statEntity = statEntity;
        this.rows = rows;
        this.values = values;
    }

    public StatEntity getStatEntity() {
        return statEntity;
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Value> getValues() {
        return values;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatHolder)) {
            return false;
        }

        final StatHolder that = (StatHolder) o;

        if (!statEntity.equals(that.statEntity)) {
            return false;
        }
        if (!rows.equals(that.rows)) {
            return false;
        }
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        int result = statEntity.hashCode();
        result = 31 * result + rows.hashCode();
        result = 31 * result + values.hashCode();
        return result;
    }
}
