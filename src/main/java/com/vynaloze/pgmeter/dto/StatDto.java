package com.vynaloze.pgmeter.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vynaloze.pgmeter.json.Parser;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

public class StatDto {
    @NonNull
    private final Long timestamp;
    @NonNull
    private final DatasourceDto datasource;
    @NonNull
    private final String id;
    @NonNull
    private final List<Map<String, Object>> payload;

    public StatDto(@NonNull final Long timestamp, @NonNull final DatasourceDto datasource, @NonNull final String id, @NonNull final List<Map<String, Object>> payload) {
        this.timestamp = timestamp;
        this.datasource = datasource;
        this.id = id;
        this.payload = payload;
    }

    @NonNull
    public Long getTimestamp() {
        return timestamp;
    }

    @NonNull
    public DatasourceDto getDatasource() {
        return datasource;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public List<Map<String, Object>> getPayload() {
        return payload;
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
