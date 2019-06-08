package com.vynaloze.pgmeter.dto;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

public class DatasourceDto {
    @Nullable
    private Long id;
    @NonNull
    private String ip;
    @Nullable
    private String hostname;
    @Nullable
    private Integer port;
    @Nullable
    private String database;
    @Nullable
    private Map<String, String> tags;

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

    public void setId(@Nullable final Long id) {
        this.id = id;
    }

    @NonNull
    public String getIp() {
        return ip;
    }

    public void setIp(@NonNull final String ip) {
        this.ip = ip;
    }

    @Nullable
    public String getHostname() {
        return hostname;
    }

    public void setHostname(@Nullable final String hostname) {
        this.hostname = hostname;
    }

    @Nullable
    public Integer getPort() {
        return port;
    }

    public void setPort(@Nullable final Integer port) {
        this.port = port;
    }

    @Nullable
    public String getDatabase() {
        return database;
    }

    public void setDatabase(@Nullable final String database) {
        this.database = database;
    }

    @Nullable
    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(@Nullable final Map<String, String> tags) {
        this.tags = tags;
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
