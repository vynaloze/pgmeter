package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.DatasourceEntity;

import java.util.List;
import java.util.Optional;

public interface DatasourceDao {
    void save(final DatasourceEntity datasourceEntity);

    Optional<DatasourceEntity> getDatasource(final String ip, final String dbName);

    List<DatasourceEntity> getDatasources(final Long tsFrom, final Long tsTo);
}
