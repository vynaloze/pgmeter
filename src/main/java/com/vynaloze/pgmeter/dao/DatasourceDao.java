package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.entity.DatasourceEntity;

import java.util.List;
import java.util.Optional;

public interface DatasourceDao {
    boolean save(final DatasourceEntity datasourceEntity);

    Optional<Long> getDatasourceId(final String ip, final String dbName);

    List<DatasourceEntity> getDatasources(final Long tsFrom, final Long tsTo);
}
