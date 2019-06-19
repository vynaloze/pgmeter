package com.vynaloze.pgmeter.web;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class StatsHandler {
    public Mono<ServerResponse> save(final ServerRequest request) {
        final Mono<String> body = request.bodyToMono(String.class);
        return ok().contentType(APPLICATION_JSON).body(body, String.class);
    }

    public Mono<ServerResponse> getStats(final ServerRequest request) {
        final var params = request.pathVariables();
        return ok().contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(params));
    }

    public Mono<ServerResponse> getDatasources(final ServerRequest request) {
        final var params = request.pathVariables();
        return ok().contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(params));
    }

}
