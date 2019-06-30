package com.vynaloze.pgmeter.model.translate;

import javax.validation.constraints.NotNull;

public class Params {
    @NotNull
    private final Param x;
    @NotNull
    private final Param y;
    @NotNull
    private final Param dimension;

    public Params(final @NotNull Param x, final @NotNull Param y, final @NotNull Param dimension) {
        this.x = x;
        this.y = y;
        this.dimension = dimension;
    }

    @NotNull
    public Param getX() {
        return x;
    }

    @NotNull
    public Param getY() {
        return y;
    }

    @NotNull
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
