package com.vynaloze.pgmeter.model;

import org.springframework.lang.NonNull;

import java.util.List;

public class Parameters {
    @NonNull
    private final Long timestampFrom;
    @NonNull
    private final Long timestampTo;
    @NonNull
    private final String type;
    @NonNull
    private final String x;
    @NonNull
    private final String y;
    @NonNull
    private final List<String> series;

    public Parameters(final @NonNull Long timestampFrom, final @NonNull Long timestampTo, final @NonNull String type, final @NonNull String x, final @NonNull String y, final @NonNull List<String> series) {
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.type = type;
        this.x = x;
        this.y = y;
        this.series = series;
    }

    @NonNull
    public Long getTimestampFrom() {
        return timestampFrom;
    }

    @NonNull
    public Long getTimestampTo() {
        return timestampTo;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getX() {
        return x;
    }

    @NonNull
    public String getY() {
        return y;
    }

    @NonNull
    public List<String> getSeries() {
        return series;
    }
}
