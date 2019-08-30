package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.LinearStats;
import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;

import java.util.List;

public interface StatService {
    Stat saveStat(final Stat stat);

    List<Datasource> getDatasources(final Long tsFrom, final Long tsTo);

    List<Stat> getMostRecentStats(final String type);

    LinearStats getLinearStats(final TranslateRequest translateRequest);
}
