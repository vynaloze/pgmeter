package com.vynaloze.pgmeter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceController {

    @RequestMapping(method = RequestMethod.GET, value = "/ds/{tsFrom}/{tsTo}")
    ResponseEntity<?> getDatasources(final @PathVariable Long tsFrom, final @PathVariable Long tsTo) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
