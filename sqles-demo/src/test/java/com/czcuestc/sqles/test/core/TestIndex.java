package com.czcuestc.sqles.test.core;

import com.czcuestc.sqles.common.util.DateUtil;
import com.czcuestc.sqles.date.DateFormat;
import com.czcuestc.sqles.engine.query.SQLUtil;
import com.czcuestc.sqles.test.util.Constants;
import com.czcuestc.sqles.test.util.TestUtil;
import com.sqles.demo.SqlEsDemoApplication;
import com.sqles.demo.domain.TestEntity;
import com.sqles.demo.mapper.TestEntityMapper;
import com.sqles.demo.schema.TestEntityIndexMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@SpringBootTest(classes = SqlEsDemoApplication.class )
public class TestIndex {
    @Autowired
    private TestEntityMapper testEntityMapper;
    @Autowired
    private TestEntityIndexMapper testEntityIndexMapper;

    private int total = 20;

    private String defaultDatasource = "";

    @BeforeEach
    public void testCreateIndex() {
        testEntityIndexMapper.deleteIndex();
        testEntityIndexMapper.createIndex();
        List<TestEntity> testEntities = TestUtil.createList(total, 0);
        testEntityIndexMapper.bulkInsert(testEntities);
        testEntityIndexMapper.refreshIndex();
    }


    @Test
    public void testTermQuery() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where long_field=3");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(3L, result.get(0).getLongField());
    }

    @Test
    public void testTermsQuery() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where long_field in (1,3,9,-10)");
        Assertions.assertEquals(3, result.size());

        HashSet<Long> set = new HashSet<>();
        set.add(1L);
        set.add(3L);
        set.add(9L);

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testRangeQuery() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where long_field between 3 and 9");
        Assertions.assertEquals(7, result.size());

        HashSet<Long> set = new HashSet<>();
        for (long i = 3; i <= 9; i++) {
            set.add(i);
        }

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testMatchQuery() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where text_field like '%es%' limit 30");
        Assertions.assertEquals(total, result.size());

        HashSet<Long> set = new HashSet<>();
        for (long i = 0; i < total; i++) {
            set.add(i);
        }

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testBoolQueryMust() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where id between 1 and 6 and text_field like '%es%' limit 30");
        Assertions.assertEquals(6, result.size());

        HashSet<Long> set = new HashSet<>();
        for (long i = 1; i <= 6; i++) {
            set.add(i);
        }

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testBoolQueryShould() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where id between 1 and 6 or long_field =9 limit 30");
        Assertions.assertEquals(7, result.size());

        HashSet<Long> set = new HashSet<>();
        for (long i = 1; i <= 6; i++) {
            set.add(i);
        }
        set.add(9L);

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testBoolQueryMustNot() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where long_field!=3 limit 100");
        Assertions.assertEquals(19, result.size());

        HashSet<Long> set = new HashSet<>();
        set.add(3L);


        for (TestEntity testEntity : result) {
            Assertions.assertTrue(!set.contains(testEntity.getLongField()));
        }
    }

    @Test
    public void testNotIn() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where long_field not in (1,3,9) limit 100");
        Assertions.assertEquals(17, result.size());

        HashSet<Long> set = new HashSet<>();
        set.add(1L);
        set.add(3L);
        set.add(9L);


        for (TestEntity testEntity : result) {
            Assertions.assertTrue(!set.contains(testEntity.getLongField()));
        }
    }

    @Test
    public void testPrefix() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where text_field like 'es%' limit 100");
        Assertions.assertEquals(20, result.size());

        List<TestEntity> result2 = SQLUtil.search(defaultDatasource, "select * from test_entity where keyword_field like 'es7%' limit 100");
        Assertions.assertEquals(1, result2.size());
        Assertions.assertTrue(result2.get(0).getKeywordField().startsWith("es7"));
    }

    @Test
    public void testExist() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where bool_field is null limit 100");
        Assertions.assertEquals(10, result.size());

        result = SQLUtil.search(defaultDatasource, "select * from test_entity where bool_field is not null limit 100");
        Assertions.assertEquals(10, result.size());
    }

    @Test
    public void testMatchAll() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity limit 100");
        Assertions.assertEquals(20, result.size());
    }

    @Test
    public void testOrderBy() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity order by long_field desc,keyword_field asc limit 100");

        Assertions.assertEquals(20, result.size());
        Long value = Long.MAX_VALUE;
        for (TestEntity testEntity : result) {
            Assertions.assertTrue(testEntity.getLongField() < value);
            value = testEntity.getLongField();
        }
    }

    @Test
    public void testPage() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity order by long_field desc limit 6,5");
        String dsl = SQLUtil.sqlToDsl(defaultDatasource, "select * from test_entity order by long_field desc limit 6,5");

        Assertions.assertEquals(5, result.size());
        HashSet<Long> set = new HashSet<>();
        for (long i = 9; i <= 13; i++) {
            set.add(i);
        }

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testBoolField() {
        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where bool_field is true limit 100");
        String dsl = SQLUtil.sqlToDsl(defaultDatasource, "select * from test_entity where bool_field is true limit 100");

        Assertions.assertEquals(10, result.size());
        for (TestEntity testEntity : result) {
            Assertions.assertTrue(testEntity.getBoolField());
        }
    }

    @Test
    public void testDate() {
        Date startDate = DateUtil.toDate(Constants.DATE.getTime() + 10 * 3600 * 24 * 1000);
        Date endDate = DateUtil.toDate(Constants.DATE.getTime() + 15 * 3600 * 24 * 1000);

        String dateFrom = DateUtil.format(startDate, DateFormat.STRICT_DATE_TIME_MILLIS.getPattern());
        String dateTo = DateUtil.format(endDate, DateFormat.STRICT_DATE_TIME_MILLIS.getPattern());

        List<TestEntity> result = SQLUtil.search(defaultDatasource, "select * from test_entity where date_field>='" + dateFrom + "' and date_field<=" + "'" + dateTo + "'");
        Assertions.assertEquals(6, result.size());

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(testEntity.getDateField().getTime() <= endDate.getTime() && testEntity.getDateField().getTime() >= startDate.getTime());
        }
    }
}
