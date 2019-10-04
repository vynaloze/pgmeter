package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.DateEntity;
import java.util.Optional;

public interface DateDao {
    void save(final DateEntity dateEntity);

    Optional<DateEntity> getByTimestamp(final Long timestamp);
}
