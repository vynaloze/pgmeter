package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.subscription.NewStatEvent;
import com.vynaloze.pgmeter.model.subscription.StillAliveEvent;
import com.vynaloze.pgmeter.model.subscription.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final CopyOnWriteArrayList<Subscription> subscriptions = new CopyOnWriteArrayList<>();

    @Override
    public Subscription subscribe(final List<Long> ids, final List<String> types) {
        final var emitter = new SseEmitter();
        final var subscription = new Subscription(emitter, ids, types);
        emitter.onCompletion(() -> this.subscriptions.remove(subscription));
        emitter.onTimeout(() -> {
            emitter.complete();
            this.subscriptions.remove(subscription);
        });
        this.subscriptions.add(subscription);
        return subscription;
    }

    @Override
    public void handleNewStatEvent(final Stat stat) {
        final var event = new NewStatEvent(stat.getTimestamp(), stat.getDatasource().getId(), stat.getId());
        this.sendEvent(event, sub ->
                sub.getDatasources().stream().anyMatch(id -> stat.getDatasource().getId().equals(id)
                        && sub.getStatTypes().stream().anyMatch(type -> stat.getId().equals(type))));
    }

    @Override
    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void keepConnectionAlive() {
        this.sendEvent(new StillAliveEvent());
    }

    private void sendEvent(final Object event, final Predicate<Subscription> filter) {
        final var deadSubs = new ArrayList<Subscription>();
        this.subscriptions.stream()
                .filter(filter)
                .forEach(sub -> {
                    try {
                        sub.getEmitter().send(event);
                    } catch (final Exception e) {
                        deadSubs.add(sub);
                    }
                });
        this.subscriptions.removeAll(deadSubs);
    }

    private void sendEvent(final Object event) {
        this.sendEvent(event, s -> true);
    }
}
