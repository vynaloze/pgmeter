package com.vynaloze.pgmeter.service;

import java.util.Optional;
import java.util.function.Supplier;

class SynchronizedDataProvider<T> {
    private final Supplier<Optional<T>> reader;
    private final Runnable writer;

    SynchronizedDataProvider(final Supplier<Optional<T>> reader, final Runnable writer) {
        this.reader = reader;
        this.writer = writer;
    }

    T get() {
        var readObj = this.reader.get();
        if (readObj.isEmpty()) {
            synchronized (this) {
                readObj = this.reader.get();
                if (readObj.isEmpty()) {
                    this.writer.run();
                    readObj = this.reader.get();
                }
            }
        }
        return readObj.get();
    }

}
