package com.vynaloze.pgmeter.dao.entity;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class DatasourceEntity {
    @NonNull
    private final Long id;
    @NonNull
    private final String ip;
    @Nullable
    private final String hostname;
    @Nullable
    private final Integer port;
    @NonNull
    private final String database;
    @Nullable
    private final String tags;

    public DatasourceEntity(@NonNull final Long id, @NonNull final String ip, @Nullable final String hostname,
                            @Nullable final Integer port, @NonNull final String database, @Nullable final String tags) {
        this.id = id;
        this.ip = ip;
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.tags = tags;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    @NonNull
    public String getIp() {
        return ip;
    }

    @Nullable
    public String getHostname() {
        return hostname;
    }

    @Nullable
    public Integer getPort() {
        return port;
    }

    @NonNull
    public String getDatabase() {
        return database;
    }

    @Nullable
    public String getTags() {
        return tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatasourceEntity)) {
            return false;
        }

        final DatasourceEntity that = (DatasourceEntity) o;

        if (!id.equals(that.id)) {
            return false;
        }
        if (!ip.equals(that.ip)) {
            return false;
        }
        if (hostname != null ? !hostname.equals(that.hostname) : that.hostname != null) {
            return false;
        }
        if (port != null ? !port.equals(that.port) : that.port != null) {
            return false;
        }
        if (!database.equals(that.database)) {
            return false;
        }
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + ip.hashCode();
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + database.hashCode();
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
