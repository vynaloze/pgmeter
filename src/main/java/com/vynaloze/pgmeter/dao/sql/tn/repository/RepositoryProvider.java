package com.vynaloze.pgmeter.dao.sql.tn.repository;

import com.vynaloze.pgmeter.dao.sql.tn.entity.BaseStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class RepositoryProvider {
    private final List<Repository> repositories;

    @Autowired
    public RepositoryProvider(final NetRepository netRepository, final PgStatActivityRepository pgStatActivityRepository) {
        this.repositories = List.of(netRepository, pgStatActivityRepository);
    }

    public <T extends BaseStat> Repository<T> getRepository(final Class clazz) {
        for (final var repository : repositories) {
            final Type[] genericInterfaces = repository.getClass().getGenericInterfaces();
            for (final Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    final Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                    for (final Type genericType : genericTypes) {
                        if (clazz.equals(genericType)) {
                            return repository;
                        }
                    }
                }
            }
        }
        throw new RuntimeException("repository for given type not found");
    }
}
