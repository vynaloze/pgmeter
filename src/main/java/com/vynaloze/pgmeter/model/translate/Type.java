package com.vynaloze.pgmeter.model.translate;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("timestamp")
    TIMESTAMP,
    @JsonProperty("datasource")
    DATASOURCE,
    @JsonProperty("key")
    KEY,
}
