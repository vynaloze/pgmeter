package com.vynaloze.pgmeter.dao.model;

import javax.validation.constraints.NotNull;

public class DatasourceEntity {
    private final Long id;
    @NotNull
    private final String ip;

    private final String hostname;

    private final Integer port;
    @NotNull
    private final String database;

    private final String tags;

    public DatasourceEntity(final Long id, @NotNull final String ip, final String hostname,
                            final Integer port, @NotNull final String database, final String tags) {
        this.id = id;
        this.ip = ip;
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.tags = tags;
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

    @NotNull
    public String getDatabase() {
        return database;
    }


    public String getTags() {
        return tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DatasourceEntity that = (DatasourceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!ip.equals(that.ip)) return false;
        if (hostname != null ? !hostname.equals(that.hostname) : that.hostname != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (!database.equals(that.database)) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + ip.hashCode();
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + database.hashCode();
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
