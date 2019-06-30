package com.vynaloze.pgmeter.model.translate;

import javax.validation.constraints.NotNull;

public class TranslateRequest {
    @NotNull
    private final Filter filter;
    @NotNull
    private final Params params;

    public TranslateRequest(final @NotNull Filter filter, final @NotNull Params params) {
        this.filter = filter;
        this.params = params;
    }

    @NotNull
    public Filter getFilter() {
        return filter;
    }

    @NotNull
    public Params getParams() {
        return params;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranslateRequest)) {
            return false;
        }

        final TranslateRequest that = (TranslateRequest) o;

        if (!filter.equals(that.filter)) {
            return false;
        }
        return params.equals(that.params);
    }

    @Override
    public int hashCode() {
        int result = filter.hashCode();
        result = 31 * result + params.hashCode();
        return result;
    }
}
