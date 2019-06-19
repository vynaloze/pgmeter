package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.dto.StatDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatsController {

    @RequestMapping(method = RequestMethod.POST, value = "/stats")
    ResponseEntity<?> save(@RequestBody final StatDto dto) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats/{type}/{tsFrom}/{tsTo}")
    ResponseEntity<?> getMetrics(final @PathVariable String type, final @PathVariable Long tsFrom, final @PathVariable Long tsTo) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
