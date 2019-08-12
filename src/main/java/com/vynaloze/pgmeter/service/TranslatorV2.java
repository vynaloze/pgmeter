package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.model.FlatStat;
import com.vynaloze.pgmeter.model.Dataset;
import com.vynaloze.pgmeter.model.LinearStats;
import com.vynaloze.pgmeter.model.translate.Param;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class TranslatorV2 {
    public LinearStats translate(final List<FlatStat> stats, final TranslateRequest translateRequest) {
        final var dimension = translateRequest.getParams().getDimension();
        final var xParam = translateRequest.getParams().getX();
        final var yParam = translateRequest.getParams().getY();

        final var groupedByX = groupByXParam(stats, xParam);

        final var labels = new ArrayList<>(new TreeSet<Object>(groupedByX.keySet()));
        final var datasets = stats.stream()
                .map(s -> getValue(s, dimension))
                .distinct()
                .map(dim -> new Dataset(dim, new ArrayList<>()))
                .collect(Collectors.toList());

        groupedByX.forEach((x, xStats) -> {
            datasets.forEach(ds -> {
                final var dimensionStats = xStats.stream().filter(fs -> getValue(fs, dimension).equals(ds.getLabel())).collect(Collectors.toList());
                if (dimensionStats.size() == 0) {
                    ds.getData().add(null);
                } else if (dimensionStats.size() == 1) {
                    ds.getData().add(getValue(dimensionStats.get(0), yParam));
                } else {
                    throw new TranslateException("Multiple y values for single x and dimension");
                }
            });

        });
        return new LinearStats(labels, datasets);
    }


    private TreeMap<?, List<FlatStat>> groupByXParam(final List<FlatStat> stats, final Param param) {
        switch (param.getType()) {
            case TIMESTAMP:
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(FlatStat::getTimestamp)));
            case DATASOURCE:
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(FlatStat::getDatasourceId)));
            case KEY:
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(s -> s.getValues().get(param.getName()))));
            default:
                throw new IllegalStateException("unrecognized enum type: " + param.getType().name());
        }
    }

    private Object getValue(final FlatStat stat, final List<Param> params) {
        if (params.size() == 1) {
            return getValue(stat, params.get(0));
        }
        return params.stream()
                .map(p -> getValue(stat, p))
                .collect(Collectors.toList());
    }

    private Object getValue(final FlatStat stat, final Param param) {
        switch (param.getType()) {
            case TIMESTAMP:
                return stat.getTimestamp();
            case DATASOURCE:
                return stat.getDatasourceId();
            case KEY:
                if (!stat.getValues().containsKey(param.getName())) {
                    throw new TranslateException("Key \"" + param.getName() + "\" does not exist");
                }
                return stat.getValues().get(param.getName());
            default:
                throw new IllegalStateException("unrecognized enum type: " + param.getType().name());
        }
    }
}
