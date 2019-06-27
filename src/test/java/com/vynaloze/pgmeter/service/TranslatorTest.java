package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.model.FlatStat;
import com.vynaloze.pgmeter.model.LinearStat;
import com.vynaloze.pgmeter.model.Point;
import com.vynaloze.pgmeter.model.translate.Param;
import com.vynaloze.pgmeter.model.translate.Params;
import com.vynaloze.pgmeter.model.translate.TranslateRequest;
import com.vynaloze.pgmeter.model.translate.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TranslatorTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private final FlatStat s0 = new FlatStat(10L, 0L, "type",
            Map.of("key0", "val00",
                    "key1", 10,
                    "key2", 20,
                    "key3", "val30"));
    private final FlatStat s1 = new FlatStat(11L, 0L, "type",
            Map.of("key0", "val00",
                    "key1", 10,
                    "key2", 21,
                    "key3", "val31"));
    private final FlatStat s2 = new FlatStat(12L, 0L, "type",
            Map.of("key0", "val00",
                    "key1", 11,
                    "key2", 20,
                    "key3", "val32"));
    private final FlatStat s3 = new FlatStat(13L, 0L, "type",
            Map.of("key0", "val01",
                    "key1", 11,
                    "key2", 21,
                    "key3", "val33"));
    private final FlatStat s4 = new FlatStat(10L, 1L, "type",
            Map.of("key0", "val01",
                    "key1", 11,
                    "key2", 20,
                    "key3", "val34"));
    private final FlatStat s5 = new FlatStat(11L, 1L, "type",
            Map.of("key0", "val01",
                    "key1", 11,
                    "key2", 21,
                    "key3", "val35"));
    private final FlatStat s6 = new FlatStat(12L, 1L, "type",
            Map.of("key0", "val02",
                    "key1", 11,
                    "key2", 20,
                    "key3", "val36"));
    private final FlatStat s7 = new FlatStat(13L, 1L, "type",
            Map.of("key0", "val02",
                    "key1", 12,
                    "key2", 21,
                    "key3", "val37"));
    private final List<FlatStat> flatStats = List.of(s0, s1, s2, s3, s4, s5, s6, s7);

    private final TranslateRequest keyByDatasourceRequest = new TranslateRequest(null, new Params(new Param("timestamp", Type.TIMESTAMP), new Param("key2", Type.KEY), new Param("datasource", Type.DATASOURCE)));
    private final TranslateRequest keyByCompositeKeyRequest = new TranslateRequest(null, new Params(new Param("timestamp", Type.TIMESTAMP), new Param("key3", Type.KEY), new Param("key0.key1", Type.COMPOSITE_KEY)));
    private final TranslateRequest keyByKeyNoTsRequest = new TranslateRequest(null, new Params(new Param("datasource", Type.DATASOURCE), new Param("key1", Type.KEY), new Param("key2", Type.KEY)));

    private final List<LinearStat> keyByDatasourceExpected = List.of(
            new LinearStat(0L, List.of(new Point(10L, 20), new Point(11L, 21), new Point(12L, 20), new Point(13L, 21))),
            new LinearStat(1L, List.of(new Point(10L, 20), new Point(11L, 21), new Point(12L, 20), new Point(13L, 21)))
    );
    private final List<LinearStat> keyByCompositeKeyExpected = List.of(
            new LinearStat("val00.10", List.of(new Point(10L, "val30"), new Point(11L, "val31"))),
            new LinearStat("val00.11", List.of(new Point(12L, "val32"))),
            new LinearStat("val01.11", List.of(new Point(13L, "val33"), new Point(10L, "val34"), new Point(11L, "val35"))),
            new LinearStat("val02.11", List.of(new Point(12L, "val36"))),
            new LinearStat("val02.12", List.of(new Point(13L, "val37")))
    );
    private final List<LinearStat> keyByKeyNoTsExpected = List.of(
            new LinearStat(20, List.of(new Point(0L, 10), new Point(0L, 11), new Point(1L, 11), new Point(1L, 11))),
            new LinearStat(21, List.of(new Point(0L, 10), new Point(0L, 11), new Point(1L, 11), new Point(1L, 12)))
    );

    private final List<TestEntry> testTable = List.of(
            new TestEntry("keyByDatasource", flatStats, keyByDatasourceRequest, keyByDatasourceExpected),
            new TestEntry("keyByCompositeKey", flatStats, keyByCompositeKeyRequest, keyByCompositeKeyExpected),
            new TestEntry("keyByKeyNoTs", flatStats, keyByKeyNoTsRequest, keyByKeyNoTsExpected)
    );

    @Test
    public void shouldTranslate() {
        final var translator = new Translator();
        for (final var entry : testTable) {
            LOGGER.info(entry.getTestCase());
            final var actual = translator.translate(entry.getGiven(), entry.getRequest());
            assertThat(actual).containsExactlyInAnyOrder(entry.getExpected().toArray(new LinearStat[0]));
        }
    }

    private class TestEntry {
        private final String testCase;
        private final List<FlatStat> given;
        private final TranslateRequest request;
        private final List<LinearStat> expected;

        TestEntry(final String testCase, final List<FlatStat> given, final TranslateRequest request, final List<LinearStat> expected) {
            this.testCase = testCase;
            this.given = given;
            this.request = request;
            this.expected = expected;
        }

        String getTestCase() {
            return testCase;
        }

        List<FlatStat> getGiven() {
            return given;
        }

        TranslateRequest getRequest() {
            return request;
        }

        List<LinearStat> getExpected() {
            return expected;
        }
    }
}
