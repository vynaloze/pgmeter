package com.vynaloze.pgmeter.dao.sql.tn;

import com.vynaloze.pgmeter.dao.sql.StatDao;
import com.vynaloze.pgmeter.dao.sql.tn.entity.BaseStat;
import com.vynaloze.pgmeter.dao.sql.tn.repository.RepositoryProvider;
import com.vynaloze.pgmeter.dto.DatasourceDto;
import com.vynaloze.pgmeter.dto.StatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StatDaoTN implements StatDao {
    private final RepositoryProvider repositoryProvider;

    @Autowired
    public StatDaoTN(final RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    @Override
    public void save(final StatDto statDto) {
        final var clazz = getClazzFrom(statDto.getId());
        final var stat = Converter.toStat(statDto, clazz);
        repositoryProvider.getRepository(clazz).saveAll(stat);
    }

    @Override
    public List<StatDto> getStats(final Long tsFrom, final Long tsTo, final String type) {
        final var clazz = getClazzFrom(type);
        final var snakeType = Converter.toSnakeCase(type);
        final var stats = repositoryProvider.getRepository(clazz).findByTimestampBetween(tsFrom, tsTo);
        return stats.stream().collect((Collectors.groupingBy(BaseStat::getTimestamp)))
                .values().stream().map(baseStats -> Converter.toDto(baseStats, snakeType))
                .collect(Collectors.toList());
    }

    @Override
    public List<DatasourceDto> getDatasources(final Long tsFrom, final Long tsTo) {
        return null;
    }

    private Class getClazzFrom(final String type) {
        final var camelCaseName = Converter.toCamelCase(type);
        try {
            return Class.forName(camelCaseName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
