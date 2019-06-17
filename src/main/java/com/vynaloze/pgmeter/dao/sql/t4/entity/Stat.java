package com.vynaloze.pgmeter.dao.sql.t4.entity;

import com.vynaloze.pgmeter.dao.sql.Datasource;
import org.springframework.lang.NonNull;

public class Stat {
    @NonNull
    private final Long id;
    @NonNull
    private final Long timestamp;
    @NonNull
    private final Datasource datasource;
    @NonNull
    private final String type;

    public Stat(final Long id, final Long timestamp, final Datasource datasource, final String type) {
        this.id = id;
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stat)) {
            return false;
        }

        final Stat stat = (Stat) o;

        if (!id.equals(stat.id)) {
            return false;
        }
        if (!timestamp.equals(stat.timestamp)) {
            return false;
        }
        if (!datasource.equals(stat.datasource)) {
            return false;
        }
        return type.equals(stat.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + datasource.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
