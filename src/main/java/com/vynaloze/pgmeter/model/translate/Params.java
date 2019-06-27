package com.vynaloze.pgmeter.model.translate;

import org.springframework.lang.NonNull;

public class Params {
    @NonNull
    private final Param x;
    @NonNull
    private final Param y;
    @NonNull
    private final Param dimension;

    public Params(final @NonNull Param x, final @NonNull Param y, final @NonNull Param dimension) {
        this.x = x;
        this.y = y;
        this.dimension = dimension;
    }

    @NonNull
    public Param getX() {
        return x;
    }

    @NonNull
    public Param getY() {
        return y;
    }

    @NonNull
    public Param getDimension() {
        return dimension;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Params)) {
            return false;
        }

        final Params params = (Params) o;

        if (!x.equals(params.x)) {
            return false;
        }
        if (!y.equals(params.y)) {
            return false;
        }
        return dimension.equals(params.dimension);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + dimension.hashCode();
        return result;
    }
}
