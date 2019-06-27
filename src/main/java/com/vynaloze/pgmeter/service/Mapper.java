package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.entity.DatasourceEntity;
import com.vynaloze.pgmeter.dao.entity.StatEntity;
import com.vynaloze.pgmeter.model.Datasource;
import com.vynaloze.pgmeter.model.Stat;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public StatEntity toEntity(final Stat stat, final Long datasourceId) {
        return null;
    }

    public Stat toStat(final StatEntity statEntity) {
        return null;
    }

    public DatasourceEntity toEntity(final Datasource datasource) {
        return null;
    }

    public Datasource toModel(final DatasourceEntity entity) {
        return null;
    }


}
