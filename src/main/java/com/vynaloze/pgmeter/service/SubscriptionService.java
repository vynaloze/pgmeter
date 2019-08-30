package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.subscription.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription subscribe(final List<Long> ids, final List<String> types);

    void handleNewStatEvent(final Stat stat);

    void keepConnectionAlive();
}
