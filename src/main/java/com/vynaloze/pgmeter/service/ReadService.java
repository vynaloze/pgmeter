package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.TranslatedStats;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;

import java.util.List;

public interface ReadService {
    List<Datasource> getDatasources(final Long tsFrom, final Long tsTo);

    List<Stat> getMostRecentStats(final String type);

    List<TranslatedStats> getTranslatedStats(final TranslateRequest translateRequest);
}
