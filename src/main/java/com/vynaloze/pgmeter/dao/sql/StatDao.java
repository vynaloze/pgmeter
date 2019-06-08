package com.vynaloze.pgmeter.dao.sql;

import com.vynaloze.pgmeter.dto.StatDto;

import java.util.List;

public interface StatDao {
    void save(StatDto stat);

    List<StatDto> get(Long tsFrom, Long tsTo, String type);
}
