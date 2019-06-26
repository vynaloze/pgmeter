package com.vynaloze.pgmeter.model;

import org.springframework.lang.NonNull;

public class Point {
    @NonNull
    private final Object x;
    @NonNull
    private final Object y;

    public Point(final @NonNull Object x, final @NonNull Object y) {
        this.x = x;
        this.y = y;
    }

    @NonNull
    public Object getX() {
        return x;
    }

    @NonNull
    public Object getY() {
        return y;
    }
}
