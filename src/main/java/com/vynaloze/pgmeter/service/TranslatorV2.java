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
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TranslatorV2 {
    public LinearStats translate(final List<FlatStat> stats, final TranslateRequest translateRequest) {
        final var dimension = translateRequest.getParams().getDimension();
        final var xParam = translateRequest.getParams().getX();
        final var yParam = translateRequest.getParams().getY();

        final var groupedByX = groupByParam(stats, xParam);
        final var groupedByDimension = groupByParam(stats, dimension);

        final var labels = new ArrayList<>(new TreeSet<Object>(groupedByX.keySet()));
        final var datasets = groupedByDimension.keySet().stream()
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
                    throw new TranslateException("Multiple y values for single x and single dimension");
                }
            });

        });
        return new LinearStats(labels, datasets);
    }


    private TreeMap<?, List<FlatStat>> groupByParam(final List<FlatStat> stats, final Param param) {
        switch (param.getType()) {
            case TIMESTAMP:
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(FlatStat::getTimestamp)));
            case DATASOURCE:
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(FlatStat::getDatasourceId)));
            case KEY:
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(s -> s.getValues().get(param.getName()))));
            case COMPOSITE_KEY:
                final var simpleKeys = Pattern.compile("\\.").split(param.getName());
                final Function<FlatStat, String> compositeKey = stat ->
                        Stream.of(simpleKeys)
                                .map(key -> stat.getValues().get(key).toString())
                                .collect(Collectors.joining("."));
                return new TreeMap<>(stats.stream().collect(Collectors.groupingBy(compositeKey)));
            default:
                throw new IllegalStateException("unrecognized enum type: " + param.getType().name());
        }
    }

    private Object getValue(final FlatStat stat, final Param param) {
        switch (param.getType()) {
            case TIMESTAMP:
                return stat.getTimestamp();
            case DATASOURCE:
                return stat.getDatasourceId();
            case KEY:
                return stat.getValues().get(param.getName());
            case COMPOSITE_KEY:
                return Stream.of(Pattern.compile("\\.").split(param.getName()))
                        .map(key -> stat.getValues().get(key).toString())
                        .collect(Collectors.joining("."));
            default:
                throw new IllegalStateException("unrecognized enum type: " + param.getType().name());
        }
    }
}
