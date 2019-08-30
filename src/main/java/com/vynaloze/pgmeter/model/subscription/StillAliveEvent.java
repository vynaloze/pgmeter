package com.vynaloze.pgmeter.model.subscription;

public class StillAliveEvent {
    private final boolean stillAlive;

    public StillAliveEvent() {
        this.stillAlive = true;
    }

    public boolean isStillAlive() {
        return stillAlive;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StillAliveEvent)) {
            return false;
        }

        final StillAliveEvent that = (StillAliveEvent) o;

        return stillAlive == that.stillAlive;
    }

    @Override
    public int hashCode() {
        return (stillAlive ? 1 : 0);
    }
}
