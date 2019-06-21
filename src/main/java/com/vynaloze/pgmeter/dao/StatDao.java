package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.dto.StatDto;

import java.util.List;

public interface StatDao {
    void save(StatDto stat);

    List<StatDto> getStats(Long tsFrom, Long tsTo, String type);

    List<DatasourceDto> getDatasources(Long tsFrom, Long tsTo);
}
