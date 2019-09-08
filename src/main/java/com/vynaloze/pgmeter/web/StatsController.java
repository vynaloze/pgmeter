package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import com.vynaloze.pgmeter.service.StatService;
import com.vynaloze.pgmeter.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") //fixme in prod
public class StatsController implements StatsApi {
    private final StatService statService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public StatsController(final StatService statService, final SubscriptionService subscriptionService) {
        this.statService = statService;
        this.subscriptionService = subscriptionService;
    }

// todo - exception handling

    @Override
    public ResponseEntity<?> save(final Stat stat) {
        final var savedStat = statService.saveStat(stat);
        subscriptionService.handleNewStatEvent(savedStat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getDatasources(final Long tsFrom, final Long tsTo) {
        return new ResponseEntity<>(statService.getDatasources(tsFrom, tsTo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMostRecentStats(final String type) {
        return new ResponseEntity<>(statService.getMostRecentStats(type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLinearStats(final TranslateRequest translateRequest) {
        return new ResponseEntity<>(statService.getTranslatedStats(translateRequest), HttpStatus.OK);
    }
}
