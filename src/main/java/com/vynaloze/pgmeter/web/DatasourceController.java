package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.dao.StatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceController {
    private final StatDao dao;

    @Autowired
    public DatasourceController(final StatDao dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ds/{tsFrom}/{tsTo}")
    ResponseEntity<?> getDatasources(final @PathVariable Long tsFrom, final @PathVariable Long tsTo) {
        final var ds = dao.getDatasources(tsFrom, tsTo);
        return new ResponseEntity<>(ds, HttpStatus.OK);
    }
}
