package com.vynaloze.pgmeter.model;

import com.vynaloze.pgmeter.model.translate.Params;

import javax.validation.constraints.NotNull;

public class TranslatedStats {
    @NotNull
    private final Params params;
    @NotNull
    private final LinearStats data;

    public TranslatedStats(@NotNull final Params params, @NotNull final LinearStats data) {
        this.params = params;
        this.data = data;
    }

    @NotNull
    public Params getParams() {
        return params;
    }

    @NotNull
    public LinearStats getData() {
        return data;
    }
}
