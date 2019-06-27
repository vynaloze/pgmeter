package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.entity.DatasourceEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatasourceDaoImpl implements DatasourceDao {
    @Override
    public boolean save(final DatasourceEntity datasourceEntity) {
        return false;
    }

    @Override
    public Optional<Long> getDatasourceId(final String ip, final String dbName) {
        return Optional.empty();
    }

    @Override
    public List<DatasourceEntity> getDatasources(final Long tsFrom, final Long tsTo) {
        return null;
    }
}
