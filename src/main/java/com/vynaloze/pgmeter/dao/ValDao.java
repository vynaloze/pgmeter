package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.ValEntity;

import java.util.Optional;

public interface ValDao {
    void save(final ValEntity valEntity);

    Optional<ValEntity> getById(final Long id);

    Optional<ValEntity> getByKey(final String key);
}
