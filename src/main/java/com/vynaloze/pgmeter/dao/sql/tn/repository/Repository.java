package com.vynaloze.pgmeter.dao.sql.tn.repository;

import com.vynaloze.pgmeter.dao.sql.tn.entity.BaseStat;

import java.util.List;

public interface Repository<T extends BaseStat> {
    <S extends T> Iterable<S> saveAll(Iterable<S> var1);

    List<T> findByTimestampBetween(Long tsFrom, Long tsTo);
}
