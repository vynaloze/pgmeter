package com.vynaloze.pgmeter.model;

import javax.validation.constraints.NotNull;

public class Point {
    @NotNull
    private final Object x;
    @NotNull
    private final Object y;

    public Point(final @NotNull Object x, final @NotNull Object y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public Object getX() {
        return x;
    }

    @NotNull
    public Object getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }

        final Point point = (Point) o;

        if (!x.equals(point.x)) {
            return false;
        }
        return y.equals(point.y);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
