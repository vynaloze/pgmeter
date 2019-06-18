package com.vynaloze.pgmeter.dao.sql.tn.repository;

import com.vynaloze.pgmeter.dao.sql.tn.entity.Net;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NetRepository extends CrudRepository<Net, Long>, Repository<Net> {
    @Override
    List<Net> findByTimestampBetween(Long tsFrom, Long tsTo);
}
