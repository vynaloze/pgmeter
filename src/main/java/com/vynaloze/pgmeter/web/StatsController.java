package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import com.vynaloze.pgmeter.service.ReadService;
import com.vynaloze.pgmeter.service.SubscriptionService;
import com.vynaloze.pgmeter.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") //fixme in prod
public class StatsController implements StatsApi {
    private final ReadService readService;
    private final WriteService writeService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public StatsController(final ReadService readService, final WriteService writeService, final SubscriptionService subscriptionService) {
        this.readService = readService;
        this.writeService = writeService;
        this.subscriptionService = subscriptionService;
    }
// todo - exception handling

    @Override
    public ResponseEntity<?> save(final Stat stat) {
        final var savedStat = writeService.save(stat);
        subscriptionService.handleNewStatEvent(savedStat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getDatasources(final Long tsFrom, final Long tsTo) {
        return new ResponseEntity<>(readService.getDatasources(tsFrom, tsTo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMostRecentStats(final String type) {
        return new ResponseEntity<>(readService.getMostRecentStats(type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLinearStats(final TranslateRequest translateRequest) {
        return new ResponseEntity<>(readService.getTranslatedStats(translateRequest), HttpStatus.OK);
    }
}
