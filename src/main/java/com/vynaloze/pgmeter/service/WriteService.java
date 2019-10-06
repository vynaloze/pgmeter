package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.model.Stat;

public interface WriteService {
    Stat save(final Stat stat);
}
