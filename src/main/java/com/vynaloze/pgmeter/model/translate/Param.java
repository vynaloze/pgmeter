package com.vynaloze.pgmeter.model.translate;

import javax.validation.constraints.NotNull;

public class Param {
    @NotNull
    private final String name;
    @NotNull
    private final Type type;

    public Param(final @NotNull String name, final @NotNull Type type) {
        this.name = name;
        this.type = type;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Param)) {
            return false;
        }

        final Param param = (Param) o;

        if (!name.equals(param.name)) {
            return false;
        }
        return type == param.type;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}