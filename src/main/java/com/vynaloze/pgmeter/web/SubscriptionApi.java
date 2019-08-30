package com.vynaloze.pgmeter.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SubscriptionApi {
    @RequestMapping(method = RequestMethod.GET, value = "/api/subscribe/{ids}/{types}")
    SseEmitter subscribe(final @PathVariable Long[] ids, final @PathVariable String[] types);
}
