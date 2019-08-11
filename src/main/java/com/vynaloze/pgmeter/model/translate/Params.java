package com.vynaloze.pgmeter.model.translate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Params {
    @NotNull
    private final Param x;
    @NotNull
    private final Param y;
    @NotNull
    private final List<Param> dimension;

    public Params(final @NotNull Param x, final @NotNull Param y, final @NotNull List<Param> dimension) {
        this.x = x;
        this.y = y;
        this.dimension = dimension;
    }

    @JsonIgnore
    public Params(@NotNull final Param x, @NotNull final Param y, @NotNull final Param dimension) {
        this.x = x;
        this.y = y;
        this.dimension = List.of(dimension);
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
    public List<Param> getDimension() {
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
