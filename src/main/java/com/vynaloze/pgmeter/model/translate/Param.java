package com.vynaloze.pgmeter.model.translate;

import org.springframework.lang.NonNull;

public class Param {
    @NonNull
    private final String name;
    @NonNull
    private final Type type;

    public Param(final @NonNull String name, final @NonNull Type type) {
        this.name = name;
        this.type = type;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
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