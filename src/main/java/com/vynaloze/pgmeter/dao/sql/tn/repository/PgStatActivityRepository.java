package com.vynaloze.pgmeter.dao.sql.tn.repository;

import com.vynaloze.pgmeter.dao.sql.tn.entity.PgStatActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PgStatActivityRepository extends CrudRepository<PgStatActivity, Long>, Repository<PgStatActivity> {
    @Override
    List<PgStatActivity> findByTimestampBetween(Long tsFrom, Long tsTo);
}
