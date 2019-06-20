package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.dao.StatDao;
import com.vynaloze.pgmeter.dto.Converter;
import com.vynaloze.pgmeter.dto.StatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class StatsHandler {
    private final StatDao statDao;

    @Autowired
    public StatsHandler(final StatDao statDao) {
        this.statDao = statDao;
    }

    public Mono<ServerResponse> save(final ServerRequest request) {
        return request
                .bodyToMono(StatDto.class)
                .map(Converter::toEntity)
                .flatMap(statDao::save)
                .flatMap(v -> ok().build())
                .onErrorResume(v -> badRequest().body(fromObject(v.getMessage()))); //todo better handling
    }

    public Mono<ServerResponse> getStats(final ServerRequest request) {
        final var params = request.pathVariables();
        return ok().contentType(APPLICATION_JSON)
                .body(fromObject(params));
    }

    public Mono<ServerResponse> getDatasources(final ServerRequest request) {
        final var params = request.pathVariables();
        return ok().contentType(APPLICATION_JSON)
                .body(fromObject(params));
    }

}
