package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.FactEntity;
import java.util.List;

public interface FactDao {
    void save(final FactEntity factEntity);

    List<FactEntity> getMostRecent(final String type);

    List<FactEntity> get(final String type, final Long tsFrom, final Long tsTo, final List<Long> datasourceIds);
}
