package com.vynaloze.pgmeter.dao.sql;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class Datasource {
    @NonNull
    private Long id;
    @NonNull
    private String ip;
    @Nullable
    private String hostname;
    @Nullable
    private Integer port;
    @NonNull
    private String database;
    @Nullable
    private String tags;

    public Datasource(@NonNull final Long id, @NonNull final String ip, @Nullable final String hostname,
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

    public void setId(@NonNull final Long id) {
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

    @NonNull
    public String getDatabase() {
        return database;
    }

    public void setDatabase(@NonNull final String database) {
        this.database = database;
    }

    @Nullable
    public String getTags() {
        return tags;
    }

    public void setTags(@Nullable final String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Datasource)) {
            return false;
        }

        final Datasource that = (Datasource) o;

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
