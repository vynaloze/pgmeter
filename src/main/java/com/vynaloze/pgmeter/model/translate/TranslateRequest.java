package com.vynaloze.pgmeter.model.translate;

import org.springframework.lang.NonNull;

public class TranslateRequest {
    @NonNull
    private final Filter filter;
    @NonNull
    private final Params params;

    public TranslateRequest(final @NonNull Filter filter, final @NonNull Params params) {
        this.filter = filter;
        this.params = params;
    }

    @NonNull
    public Filter getFilter() {
        return filter;
    }

    @NonNull
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
