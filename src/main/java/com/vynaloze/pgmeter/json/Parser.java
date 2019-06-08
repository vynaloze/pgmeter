package com.vynaloze.pgmeter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public final class Parser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectWriter writer() {
        return mapper.writer();
    }

    public static ObjectReader reader(final Class<?> clazz) {
        return mapper.readerFor(clazz);
    }
}
