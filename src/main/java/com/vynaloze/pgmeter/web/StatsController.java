package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import com.vynaloze.pgmeter.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController implements StatsApi {
    private final StatService statService;

    @Autowired
    public StatsController(final StatService statService) {
        this.statService = statService;
    }

// todo - exception handling

    @Override
    public ResponseEntity<?> save(final Stat stat) {
        statService.saveStat(stat);
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
        return new ResponseEntity<>(statService.getLinearStats(translateRequest), HttpStatus.OK);
    }
}
