package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
public class SubscriptionController implements SubscriptionApi {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(final SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public SseEmitter subscribe(final Long[] ids, final String[] types) {
        final var subscription = subscriptionService.subscribe(List.of(ids), List.of(types));
        return subscription.getEmitter();
    }
}
