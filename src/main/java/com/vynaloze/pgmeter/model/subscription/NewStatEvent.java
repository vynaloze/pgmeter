package com.vynaloze.pgmeter.model.subscription;

import javax.validation.constraints.NotNull;

public class NewStatEvent {
    @NotNull
    private final Long timestamp;
    @NotNull
    private final Long datasourceId;
    @NotNull
    private final String type;

    public NewStatEvent(@NotNull final Long timestamp, @NotNull final Long datasourceId, @NotNull final String type) {
        this.timestamp = timestamp;
        this.datasourceId = datasourceId;
        this.type = type;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewStatEvent)) {
            return false;
        }

        final NewStatEvent event = (NewStatEvent) o;

        if (!timestamp.equals(event.timestamp)) {
            return false;
        }
        if (!datasourceId.equals(event.datasourceId)) {
            return false;
        }
        return type.equals(event.type);
    }

    @Override
    public int hashCode() {
        int result = timestamp.hashCode();
        result = 31 * result + datasourceId.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
