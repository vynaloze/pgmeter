package com.vynaloze.pgmeter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class Datasource {
    private final Long id;
    @NotNull
    private final String ip;

    private final String hostname;

    private final Integer port;

    private final String database;

    private final Map<String, String> tags;

    public Datasource(final Long id, @NotNull final String ip, final String hostname,
                      final Integer port, final String database, final Map<String, String> tags) {
        this.id = id;
        this.ip = ip;
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.tags = tags;
    }

    @JsonIgnore
    public Datasource(@NotNull final Datasource datasource, @NotNull final Long id) {
        this.id = id;
        this.ip = datasource.ip;
        this.hostname = datasource.hostname;
        this.port = datasource.port;
        this.database = datasource.database;
        this.tags = datasource.tags == null ? null : new HashMap<>(datasource.tags);
    }


    public Long getId() {
        return id;
    }

    @NotNull
    public String getIp() {
        return ip;
    }


    public String getHostname() {
        return hostname;
    }


    public Integer getPort() {
        return port;
    }


    public String getDatabase() {
        return database;
    }


    public Map<String, String> getTags() {
        return tags;
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
