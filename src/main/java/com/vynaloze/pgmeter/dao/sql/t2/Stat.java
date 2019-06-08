package com.vynaloze.pgmeter.dao.sql.t2;

import com.vynaloze.pgmeter.dao.sql.Datasource;
import org.springframework.lang.NonNull;

public class Stat {
    @NonNull
    private Long id;
    @NonNull
    private Long timestamp;
    @NonNull
    private Datasource datasource;
    @NonNull
    private String type;
    @NonNull
    private String payload;

    public Stat(@NonNull final Long id, @NonNull final Long timestamp, @NonNull final Datasource datasource,
                @NonNull final String type, @NonNull final String payload) {
        this.id = id;
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.type = type;
        this.payload = payload;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull final Long id) {
        this.id = id;
    }

    @NonNull
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull final Long timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(@NonNull final Datasource datasource) {
        this.datasource = datasource;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull final String type) {
        this.type = type;
    }

    @NonNull
    public String getPayload() {
        return payload;
    }

    public void setPayload(@NonNull final String payload) {
        this.payload = payload;
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
        if (!type.equals(stat.type)) {
            return false;
        }
        return payload.equals(stat.payload);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + datasource.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + payload.hashCode();
        return result;
    }
}
