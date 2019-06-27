package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.StatEntity;

import java.util.List;

public interface StatDao {
    void save(final StatEntity statEntity);

    List<StatEntity> getMostRecentStats(final String type);

    List<StatEntity> getStats(final String type, final Long tsFrom, final Long tsTo);
}
