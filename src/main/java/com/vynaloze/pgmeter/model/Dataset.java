package com.vynaloze.pgmeter.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Dataset {
    @NotNull
    private final Object label;
    @NotNull
    private final List<Object> data;

    public Dataset(@NotNull final Object label, @NotNull final List<Object> data) {
        this.label = label;
        this.data = data;
    }

    @NotNull
    public Object getLabel() {
        return label;
    }

    @NotNull
    public List<Object> getData() {
        return data;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dataset)) {
            return false;
        }

        final Dataset dataset = (Dataset) o;

        if (!label.equals(dataset.label)) {
            return false;
        }
        return data.containsAll(dataset.data) && dataset.data.containsAll(data);
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "label=" + label +
                ", data=" + data +
                '}';
    }
}
