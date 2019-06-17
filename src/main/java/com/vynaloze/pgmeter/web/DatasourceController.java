package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.dao.sql.StatDao;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceController {
    private final StatDao dao;

    public DatasourceController(final Environment environment, final ApplicationContext applicationContext) {
        final String dbtype = environment.getProperty("pgmeter.dbtype");
        this.dao = applicationContext.getBean(dbtype, StatDao.class);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ds/{tsFrom}/{tsTo}")
    ResponseEntity<?> getDatasources(final @PathVariable Long tsFrom, final @PathVariable Long tsTo) {
        final var ds = dao.getDatasources(tsFrom, tsTo);
        return new ResponseEntity<>(ds, HttpStatus.OK);
    }
}
