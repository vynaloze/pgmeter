package com.vynaloze.pgmeter.model.subscription;

public class Event {
    private final boolean ignored;

    public Event(final boolean ignored) {
        this.ignored = ignored;
    }

    public boolean isIgnored() {
        return ignored;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }

        final Event event = (Event) o;

        return ignored == event.ignored;
    }

    @Override
    public int hashCode() {
        return (ignored ? 1 : 0);
    }
}
