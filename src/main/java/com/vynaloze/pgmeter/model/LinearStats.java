package com.vynaloze.pgmeter.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class LinearStats {
    @NotNull
    private final List<Object> labels;
    @NotNull
    private final List<Dataset> datasets;

    public LinearStats(@NotNull final List<Object> labels, @NotNull final List<Dataset> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }

    @NotNull
    public List<Object> getLabels() {
        return labels;
    }

    @NotNull
    public List<Dataset> getDatasets() {
        return datasets;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinearStats)) {
            return false;
        }

        final LinearStats that = (LinearStats) o;

        if (!labels.containsAll(that.labels) && that.labels.containsAll(labels)) {
            return false;
        }
        return datasets.containsAll(that.datasets) && that.datasets.containsAll(datasets);
    }

    @Override
    public int hashCode() {
        int result = labels.hashCode();
        result = 31 * result + datasets.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LinearStats{" +
                "labels=" + labels +
                ", datasets=" + datasets +
                '}';
    }
}
