package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.*;
import com.vynaloze.pgmeter.dao.model.FactEntity;
import com.vynaloze.pgmeter.dao.model.GroupEntity;
import com.vynaloze.pgmeter.dao.model.ValEntity;
import com.vynaloze.pgmeter.model.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

@Service
public class WriteServiceImpl implements WriteService {
    private final DatasourceDao datasourceDao;
    private final DateDao dateDao;
    private final StatDao statDao;
    private final GroupDao groupDao;
    private final ValDao valDao;
    private final FactDao factDao;

    @Autowired
    public WriteServiceImpl(final DatasourceDao datasourceDao, final DateDao dateDao, final StatDao statDao, final GroupDao groupDao, final ValDao valDao, final FactDao factDao) {
        this.datasourceDao = datasourceDao;
        this.dateDao = dateDao;
        this.statDao = statDao;
        this.groupDao = groupDao;
        this.valDao = valDao;
        this.factDao = factDao;
    }

    @Override
    public Stat save(final Stat stat) {
        final var datasourceEntity = new SynchronizedDataProvider<>(
                () -> datasourceDao.getByIpAndDatabase(Mapper.toDatasourceEntity(stat).getIp(), Mapper.toDatasourceEntity(stat).getDatabase()),
                () -> datasourceDao.save(Mapper.toDatasourceEntity(stat))
        ).get();
        final var dateEntity = new SynchronizedDataProvider<>(
                () -> dateDao.getByTimestamp(stat.getTimestamp()),
                () -> dateDao.save(Mapper.toDateEntity(stat))
        ).get();
        final var statEntity = new SynchronizedDataProvider<>(
                () -> statDao.getByName(stat.getId()),
                () -> statDao.save(Mapper.toStatEntity(stat))
        ).get();

        stat.getPayload().forEach(group -> {
            final var groupEntity = new GroupEntity(groupDao.getNextId());
            groupDao.save(groupEntity);
            group.forEach((key, value) -> {
                final var valEntity = new SynchronizedDataProvider<>(
                        () -> valDao.getByKey(key),
                        () -> valDao.save(new ValEntity(null, key))
                ).get();
                final var bytes = SerializationUtils.serialize(value);
                final var fact = new FactEntity(null, datasourceEntity.getId(), dateEntity.getId(), statEntity.getId(),
                        groupEntity.getId(), valEntity.getId(), bytes);
                factDao.save(fact);
            });
        });
        return new Stat(stat, Mapper.toModelDatasource(datasourceEntity));
    }
}
