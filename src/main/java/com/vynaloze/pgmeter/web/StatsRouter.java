package com.vynaloze.pgmeter.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class StatsRouter {
    @Bean
    public RouterFunction<ServerResponse> route(final StatsHandler statsHandler) {
        return RouterFunctions
                .route(POST("/api/stats").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), statsHandler::save)
                .andRoute(GET("/api/stats/{type}/{tsFrom}/{tsTo}").and(accept(APPLICATION_JSON)), statsHandler::getStats)
                .andRoute(GET("/api/ds/{tsFrom}/{tsTo}").and(accept(APPLICATION_JSON)), statsHandler::getDatasources);
    }
}
