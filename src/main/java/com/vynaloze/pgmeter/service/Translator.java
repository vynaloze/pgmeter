package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.FlatStat;
import com.vynaloze.pgmeter.model.LinearStat;
import com.vynaloze.pgmeter.model.Point;
import com.vynaloze.pgmeter.model.translate.Param;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Translator {
    public List<LinearStat> translate(final List<FlatStat> stats, final TranslateRequest translateRequest) {
        final var dimension = translateRequest.getParams().getDimension();
        final var xParam = translateRequest.getParams().getX();
        final var yParam = translateRequest.getParams().getY();

        final var grouped = groupByDimension(stats, dimension);

        final var linearStats = new ArrayList<LinearStat>();
        grouped.forEach((key, value) -> {
            final var points = value.stream().map(stat -> mapStatToPoint(stat, xParam, yParam)).collect(Collectors.toList());
            linearStats.add(new LinearStat(key, points));
        });
        return linearStats;
    }


    private Map<?, List<FlatStat>> groupByDimension(final List<FlatStat> stats, final Param dimension) {
        switch (dimension.getType()) {
            case TIMESTAMP:
                return stats.stream().collect(Collectors.groupingBy(FlatStat::getTimestamp));
            case DATASOURCE:
                return stats.stream().collect(Collectors.groupingBy(FlatStat::getDatasourceId));
            case KEY:
                return stats.stream().collect(Collectors.groupingBy(s -> s.getValues().get(dimension.getName())));
            case COMPOSITE_KEY:
                final var simpleKeys = Pattern.compile("\\.").split(dimension.getName());
                final Function<FlatStat, String> compositeKey = stat ->
                        Stream.of(simpleKeys)
                                .map(key -> stat.getValues().get(key).toString())
                                .collect(Collectors.joining("."));
                return stats.stream().collect(Collectors.groupingBy(compositeKey));
            default:
                throw new IllegalStateException("unrecognized enum type: " + dimension.getType().name());
        }
    }

    private Point mapStatToPoint(final FlatStat stat, final Param paramX, final Param paramY) {
        return new Point(getValue(stat, paramX), getValue(stat, paramY));
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
