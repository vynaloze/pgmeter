package com.vynaloze.pgmeter.model.subscription;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Subscription {
    @NotNull
    private final SseEmitter emitter;
    @NotNull
    private final List<Long> datasources;
    @NotNull
    private final List<String> statTypes;

    public Subscription(@NotNull final SseEmitter emitter, @NotNull final List<Long> datasources, @NotNull final List<String> statTypes) {
        this.emitter = emitter;
        this.datasources = datasources;
        this.statTypes = statTypes;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public List<Long> getDatasources() {
        return datasources;
    }

    public List<String> getStatTypes() {
        return statTypes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subscription)) {
            return false;
        }

        final Subscription that = (Subscription) o;

        if (!emitter.equals(that.emitter)) {
            return false;
        }
        if (!datasources.containsAll(that.datasources)) {
            return false;
        }
        return statTypes.containsAll(that.statTypes);
    }

    @Override
    public int hashCode() {
        int result = emitter.hashCode();
        result = 31 * result + datasources.hashCode();
        result = 31 * result + statTypes.hashCode();
        return result;
    }
}
