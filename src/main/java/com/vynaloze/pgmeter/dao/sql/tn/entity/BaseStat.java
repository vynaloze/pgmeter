package com.vynaloze.pgmeter.dao.sql.tn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vynaloze.pgmeter.dao.sql.Datasource;
import org.springframework.lang.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseStat {
    @NonNull
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @NonNull
    @JsonIgnore
    private final Long timestamp;
    @NonNull
    @JsonIgnore
    private final Datasource datasource;

    protected BaseStat(final Long id, final Long timestamp, final Datasource datasource) {
        this.id = id;
        this.timestamp = timestamp;
        this.datasource = datasource;
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
}
