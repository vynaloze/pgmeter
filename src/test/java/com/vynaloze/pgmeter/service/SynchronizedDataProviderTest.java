package com.vynaloze.pgmeter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class SynchronizedDataProviderTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String testString = "foo";
    private SynchronizedDataProvider<String> synchronizedDataProvider;
    private DataProvider dataProvider;
    private ExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        this.dataProvider = new DataProvider();
        this.synchronizedDataProvider = new SynchronizedDataProvider<>(
                () -> this.dataProvider.read(this.testString),
                () -> this.dataProvider.write(this.testString)
        );
        this.executorService = Executors.newFixedThreadPool(3);
    }

    @After
    public void tearDown() throws Exception {
        this.synchronizedDataProvider = null;
        this.dataProvider = null;
        this.executorService.shutdown();
        this.executorService = null;
    }

    @Test
    public void shouldGetWhenPresent() {
        //given
        dataProvider.write(testString);
        //when
        final var result = synchronizedDataProvider.get();
        //then
        assertThat(result).isNotNull();
        assertThat(dataProvider.data).containsExactly(testString);
    }

    @Test
    public void shouldInsertWhenNotPresent() {
        //when
        final var result = synchronizedDataProvider.get();
        //then
        assertThat(result).isNotNull();
        assertThat(dataProvider.data).containsExactly(testString);
    }

    @Test
    public void shouldInsertOnlyOnce() throws InterruptedException {
        //given
        final List<Callable<String>> tasks = List.of(() -> synchronizedDataProvider.get(),
                () -> synchronizedDataProvider.get(), () -> synchronizedDataProvider.get());
        //when
        final var futures = executorService.invokeAll(tasks);
        //then
        futures.forEach(future -> {
            try {
                assertThat(future.get()).isNotNull();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error(e);
            }
        });
        assertThat(dataProvider.data).containsExactly(testString);
    }

    private static class DataProvider {
        private final List<String> data;

        DataProvider() {
            this.data = new ArrayList<>();
        }

        void write(final String string) {
            try {
                // simulate load: 50-150ms
                Thread.sleep(new Random().nextInt(100) + 50);
                this.data.add(string);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }

        Optional<String> read(final String string) {
            return this.data.contains(string) ? Optional.of(string) : Optional.empty();
        }
    }
}
