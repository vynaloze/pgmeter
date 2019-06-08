package com.vynaloze.pgmeter.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.json.Parser;
import org.springframework.lang.NonNull;

import java.util.Map;

public class StatDto {
    @NonNull
    private Long timestamp;
    @NonNull
    private DatasourceDto datasource;
    @NonNull
    private String id;
    @NonNull
    private Map<String, Object> payload;

    public StatDto(@NonNull final Long timestamp, @NonNull final DatasourceDto datasource, @NonNull final String id, @NonNull final Map<String, Object> payload) {
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.id = id;
        this.payload = payload;
    }

    @NonNull
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull final Long timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public DatasourceDto getDatasource() {
        return datasource;
    }

    public void setDatasource(@NonNull final DatasourceDto datasource) {
        this.datasource = datasource;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull final String id) {
        this.id = id;
    }

    @NonNull
    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(@NonNull final Map<String, Object> payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatDto)) {
            return false;
        }

        final StatDto statDto = (StatDto) o;

        if (!timestamp.equals(statDto.timestamp)) {
            return false;
        }
        if (!datasource.equals(statDto.datasource)) {
            return false;
        }
        if (!id.equals(statDto.id)) {
            return false;
        }
        return payload.equals(statDto.payload);
    }

    @Override
    public int hashCode() {
        int result = timestamp.hashCode();
        result = 31 * result + datasource.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + payload.hashCode();
        return result;
    }

    @Override
    public String toString() {
        try {
            return Parser.writer().writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
