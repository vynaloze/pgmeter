package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.StatEntity;
import java.util.Optional;

public interface StatDao {
    void save(final StatEntity statEntity);

    Optional<StatEntity> getByName(final String name);
}
