package com.czcuestc.sqles.test.core;

import com.czcuestc.sqles.test.util.TestUtil;
import com.sqles.demo.SqlEsDemoApplication;
import com.sqles.demo.domain.TestEntity;
import com.sqles.demo.schema.TestEntityIndexMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@SpringBootTest(classes = SqlEsDemoApplication.class )
public class TestIndexMapper {
    @Autowired
    private TestEntityIndexMapper testEntityIndexMapper;

    @BeforeEach
    public void rubBefore() {
        testEntityIndexMapper.deleteIndex();
        testEntityIndexMapper.createIndex();
    }

    public void initData() {
        List<TestEntity> testEntities = TestUtil.createList(20, 0);
        testEntityIndexMapper.bulkInsert(testEntities);
    }

    @Test
    public void testCreateIndex() {
        testEntityIndexMapper.deleteIndex();
        boolean result = testEntityIndexMapper.createIndex();
        Assertions.assertTrue(result);

        result = testEntityIndexMapper.existIndex();
        Assertions.assertTrue(result);

        result = testEntityIndexMapper.deleteIndex();
        Assertions.assertTrue(result);

        result = testEntityIndexMapper.existIndex();
        Assertions.assertFalse(result);

        result = testEntityIndexMapper.refreshIndex();
        Assertions.assertTrue(result);
    }

    @Test
    public void testBulkInsert() {
        initData();
        testEntityIndexMapper.refreshIndex();
        long count = testEntityIndexMapper.count();
        Assertions.assertEquals(20, count);
    }

    @Test
    public void testBulkDelete() {
        initData();
        testEntityIndexMapper.refreshIndex();

        List<Long> ids = new ArrayList<>();
        for (long i = 5; i < 10; i++) {
            ids.add(i);
        }
        long count = testEntityIndexMapper.bulkDelete(ids);
        Assertions.assertEquals(5, count);
    }

    @Test
    public void testBulkUpdate() {
        List<TestEntity> testEntities = TestUtil.createList(20, 0);
        testEntityIndexMapper.bulkInsert(testEntities);
        testEntityIndexMapper.refreshIndex();

        List<TestEntity> list = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            TestEntity testEntity = testEntities.get(i);
            testEntity.setTextField("testBulkUpdate" + i);

            list.add(testEntity);
        }
        long count = testEntityIndexMapper.bulkUpdate(list);
        Assertions.assertEquals(5, count);
        testEntityIndexMapper.refreshIndex();

        for (int i = 5; i < 10; i++) {
            TestEntity testEntity = testEntityIndexMapper.findById(Long.valueOf(i));
            Assertions.assertEquals("testBulkUpdate" + i, testEntity.getTextField());
        }
    }
}
