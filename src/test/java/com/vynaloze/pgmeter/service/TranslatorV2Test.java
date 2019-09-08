package com.vynaloze.pgmeter.service;

import com.vynaloze.pgmeter.dao.model.FlatStat;
import com.vynaloze.pgmeter.model.Dataset;
import com.vynaloze.pgmeter.model.LinearStats;
import com.vynaloze.pgmeter.model.translate.Param;
import com.vynaloze.pgmeter.model.translate.Params;
import com.vynaloze.pgmeter.model.translate.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TranslatorV2Test {
    private static final Logger LOGGER = LogManager.getLogger();

    /* GIVEN */
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
    private final List<FlatStat> flatStats = Lists.newArrayList(s0, s1, s2, s3, s4, s5, s6, s7);


    /* WHEN - VALID */
    private final Params keyByDatasourceRequest = new Params(
            new Param("timestamp", Type.TIMESTAMP),
            new Param("key2", Type.KEY),
            new Param("datasource", Type.DATASOURCE)
    );
    private final Params keyByCompositeKeyRequest = new Params(
            new Param("timestamp", Type.TIMESTAMP),
            new Param("key3", Type.KEY),
            List.of(new Param("key0", Type.KEY), new Param("key1", Type.KEY))
    );
    private final Params keyByKeyNoTsRequest = new Params(
            new Param("key3", Type.KEY),
            new Param("key1", Type.KEY),
            new Param("key2", Type.KEY)
    );
    private final Params keyByCompositeKey2Request = new Params(
            new Param("timestamp", Type.TIMESTAMP),
            new Param("key3", Type.KEY),
            List.of(new Param("ds", Type.DATASOURCE), new Param("key1", Type.KEY))
    );
    /* WHEN - INVALID */
    private final Params duplicateXValuesRequest = new Params(
            new Param("datasource", Type.DATASOURCE),
            new Param("key1", Type.KEY),
            new Param("key2", Type.KEY)
    );
    private final Params nonExistingKeyRequest = new Params(
            new Param("datasource", Type.DATASOURCE),
            new Param("invalid", Type.KEY),
            new Param("key2", Type.KEY)
    );


    /* THEN */
    private final LinearStats keyByDatasourceExpected = new LinearStats(
            Lists.newArrayList(10L, 11L, 12L, 13L),
            Lists.newArrayList(
                    new Dataset(0L, Lists.newArrayList(20, 21, 20, 21)),
                    new Dataset(1L, Lists.newArrayList(20, 21, 20, 21))
            )
    );
    private final LinearStats keyByCompositeKeyExpected = new LinearStats(
            Lists.newArrayList(10L, 11L, 12L, 13L),
            Lists.newArrayList(
                    new Dataset(Lists.newArrayList("val00", 10), Lists.newArrayList("val30", "val31", null, null)),
                    new Dataset(Lists.newArrayList("val00", 11), Lists.newArrayList(null, null, "val32", null)),
                    new Dataset(Lists.newArrayList("val01", 11), Lists.newArrayList("val34", "val35", null, "val33")),
                    new Dataset(Lists.newArrayList("val02", 11), Lists.newArrayList(null, null, "val36", null)),
                    new Dataset(Lists.newArrayList("val02", 12), Lists.newArrayList(null, null, null, "val37"))
            )
    );
    private final LinearStats keyByKeyNoTsExpected = new LinearStats(
            Lists.newArrayList("val30", "val31", "val32", "val33", "val34", "val35", "val36", "val37"),
            Lists.newArrayList(
                    new Dataset(20, Lists.newArrayList(10, null, 11, null, 11, null, 11, null)),
                    new Dataset(21, Lists.newArrayList(null, 10, null, 11, null, 11, null, 12))
            )
    );
    private final LinearStats keyByCompositeKey2Expected = new LinearStats(
            Lists.newArrayList(10L, 11L, 12L, 13L),
            Lists.newArrayList(
                    new Dataset(Lists.newArrayList(0L, 10), Lists.newArrayList("val30", "val31", null, null)),
                    new Dataset(Lists.newArrayList(0L, 11), Lists.newArrayList(null, null, "val32", "val33")),
                    new Dataset(Lists.newArrayList(1L, 11), Lists.newArrayList("val34", "val35", "val36", null)),
                    new Dataset(Lists.newArrayList(1L, 12), Lists.newArrayList(null, null, null, "val37"))
            )
    );

    @Test
    public void shouldTranslate() {
        final var translator = new TranslatorV2();
        final var testTable = Lists.newArrayList(
                new TestEntry("keyByDatasource", flatStats, keyByDatasourceRequest, keyByDatasourceExpected),
                new TestEntry("keyByCompositeKey", flatStats, keyByCompositeKeyRequest, keyByCompositeKeyExpected),
                new TestEntry("keyByKeyNoTs", flatStats, keyByKeyNoTsRequest, keyByKeyNoTsExpected),
                new TestEntry("keyByCompositeKey2", flatStats, keyByCompositeKey2Request, keyByCompositeKey2Expected)
        );

        for (final var entry : testTable) {
            LOGGER.info(entry.getTestCase());
            final var actual = translator.translate(entry.getGiven(), entry.getParams());
            assertThat(actual).isEqualTo(entry.getExpected());
        }
    }

    @Test
    public void shouldNotTranslate() {
        final var translator = new TranslatorV2();
        LOGGER.info("duplicateXValues");
        assertThatExceptionOfType(TranslateException.class).isThrownBy(() -> translator.translate(flatStats, duplicateXValuesRequest));
        LOGGER.info("nonExistingKey");
        assertThatExceptionOfType(TranslateException.class).isThrownBy(() -> translator.translate(flatStats, nonExistingKeyRequest));
    }

    private class TestEntry {
        private final String testCase;
        private final List<FlatStat> given;
        private final Params params;
        private final LinearStats expected;

        TestEntry(final String testCase, final List<FlatStat> given, final Params params, final LinearStats expected) {
            this.testCase = testCase;
            this.given = given;
            this.params = params;
            this.expected = expected;
        }

        String getTestCase() {
            return testCase;
        }

        List<FlatStat> getGiven() {
            return given;
        }

        Params getParams() {
            return params;
        }

        LinearStats getExpected() {
            return expected;
        }
    }
}
