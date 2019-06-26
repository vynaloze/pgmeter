package com.vynaloze.pgmeter.model;

import org.springframework.lang.NonNull;

import java.util.List;

public class LinearStat {
    @NonNull
    private final Object id;
    @NonNull
    private final List<Point> data;

    public LinearStat(final @NonNull Object id, final @NonNull List<Point> data) {
        this.id = id;
        this.data = data;
    }

    @NonNull
    public Object getId() {
        return id;
    }

    @NonNull
    public List<Point> getData() {
        return data;
    }
}
