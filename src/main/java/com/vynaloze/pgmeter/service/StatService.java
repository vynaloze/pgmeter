package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.LinearStat;
import com.vynaloze.pgmeter.model.Stat;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;

import java.util.List;

public interface StatService {
    boolean saveStat(final Stat stat);

    List<Datasource> getDatasources(final Long tsFrom, final Long tsTo);

    List<Stat> getMostRecentStats(final String type);

    List<LinearStat> getLinearStats(final TranslateRequest translateRequest);
}
