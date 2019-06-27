package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.entity.StatEntity;

import java.util.List;

public interface StatDao {
    boolean save(final StatEntity statEntity);

    List<StatEntity> getMostRecentStats(final String type);

    List<StatEntity> getStats(final String type, final Long tsFrom, final Long tsTo);
}
