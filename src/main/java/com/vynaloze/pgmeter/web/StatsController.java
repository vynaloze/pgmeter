package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.dao.sql.StatDao;
import com.vynaloze.pgmeter.dto.StatDto;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatsController {
    private final StatDao dao;

    public StatsController(final Environment environment, final ApplicationContext applicationContext) {
        final String dbtype = environment.getProperty("pgmeter.dbtype");
        this.dao = applicationContext.getBean(dbtype, StatDao.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/stats")
    ResponseEntity<?> save(@RequestBody final StatDto dto) {
        dao.save(dto); //todo error handling
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/{type}/{tsFrom}/{tsTo}")
    ResponseEntity<?> getMetrics(final @PathVariable String type, final @PathVariable Long tsFrom, final @PathVariable Long tsTo) {
        final var stats = dao.getStats(tsFrom, tsTo, type);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
