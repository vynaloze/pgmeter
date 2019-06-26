package com.vynaloze.pgmeter.web;

import com.vynaloze.pgmeter.model.Parameters;
import com.vynaloze.pgmeter.model.Stat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface PgMeterApi {
    @RequestMapping(method = RequestMethod.POST, value = "/api/stats")
    ResponseEntity<?> save(final @RequestBody Stat stat);

    @RequestMapping(method = RequestMethod.GET, value = "/api/ds/{tsFrom}/{tsTo}")
    ResponseEntity<?> getDatasources(final @PathVariable Long tsFrom, final @PathVariable Long tsTo);

    @RequestMapping(method = RequestMethod.GET, value = "/api/stats/{type}")
    ResponseEntity<?> getMostRecentStats(final @PathVariable String type);

    @RequestMapping(method = RequestMethod.POST, value = "/api/stats/translate")
    ResponseEntity<?> getLinearStats(final @RequestBody Parameters parameters);
}
