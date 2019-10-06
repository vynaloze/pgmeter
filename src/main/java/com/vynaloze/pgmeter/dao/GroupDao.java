package com.vynaloze.pgmeter.dao;

import com.vynaloze.pgmeter.dao.model.GroupEntity;

public interface GroupDao {
    void save(final GroupEntity groupEntity);

    Long getNextId();
}
