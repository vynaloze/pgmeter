package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.entity.StatEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatDaoImpl implements StatDao {
    @Override
    public boolean save(StatEntity statEntity) {
        return false;
    }

    @Override
    public List<StatEntity> getMostRecentStats(String type) {
        return null;
    }

    @Override
    public List<StatEntity> getStats(String type, Long tsFrom, Long tsTo) {
        return null;
    }
}
