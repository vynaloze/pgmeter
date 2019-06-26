package com.vynaloze.pgmeter.dao;


import com.vynaloze.pgmeter.dao.entity.Datasource;
import com.vynaloze.pgmeter.dao.entity.Stat;

import java.util.List;

public interface StatDao {
    boolean save(Stat stat);

    List<Datasource> getDatasources(final Long tsFrom, final Long tsTo);

    List<Stat> getAllStats(final String type, final Long tsFrom, final Long tsTo);

    List<Stat> getMostRecentStats(final String type);

    List<Stat> getStats(final String type, final Long tsFrom, final Long tsTo, final List<Object> keys);
}
