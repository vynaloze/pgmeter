package com.vynaloze.pgmeter.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

public class DatasourceDto {
    @Nullable
    private final Long id;
    @NonNull
    private final String ip;
    @Nullable
    private final String hostname;
    @Nullable
    private final Integer port;
    @Nullable
    private final String database;
    @Nullable
    private final Map<String, String> tags;

    public DatasourceDto(@Nullable final Long id, @NonNull final String ip, @Nullable final String hostname,
                         @Nullable final Integer port, @Nullable final String database, @Nullable final Map<String, String> tags) {
        this.id = id;
        this.ip = ip;
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.tags = tags;
    }

    @Nullable
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

    @Nullable
    public String getDatabase() {
        return database;
    }

    @Nullable
    public Map<String, String> getTags() {
        return tags;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatasourceDto)) {
            return false;
        }

        final DatasourceDto that = (DatasourceDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
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
        if (database != null ? !database.equals(that.database) : that.database != null) {
            return false;
        }
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + ip.hashCode();
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (database != null ? database.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
