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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinearStat)) {
            return false;
        }

        final LinearStat that = (LinearStat) o;

        if (!id.equals(that.id)) {
            return false;
        }
        return data.containsAll(that.data) && that.data.containsAll(data);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LinearStat{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}
