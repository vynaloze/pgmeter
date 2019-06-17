package com.vynaloze.pgmeter.dao.sql.t4;

import com.vynaloze.pgmeter.dao.sql.t4.entity.Row;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Stat;
import com.vynaloze.pgmeter.dao.sql.t4.entity.Value;
import org.springframework.lang.NonNull;

import java.util.List;

public class StatHolder {
    @NonNull
    private final Stat stat;
    @NonNull
    private final List<Row> rows;
    @NonNull
    private final List<Value> values;

    public StatHolder(final Stat stat, final List<Row> rows, final List<Value> values) {
        this.stat = stat;
        this.rows = rows;
        this.values = values;
    }

    public Stat getStat() {
        return stat;
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

        if (!stat.equals(that.stat)) {
            return false;
        }
        if (!rows.equals(that.rows)) {
            return false;
        }
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        int result = stat.hashCode();
        result = 31 * result + rows.hashCode();
        result = 31 * result + values.hashCode();
        return result;
    }
}
