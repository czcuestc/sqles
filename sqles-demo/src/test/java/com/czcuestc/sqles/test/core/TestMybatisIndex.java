package com.czcuestc.sqles.test.core;

import com.czcuestc.sqles.test.util.TestUtil;
import com.sqles.demo.SqlEsDemoApplication;
import com.sqles.demo.domain.TestEntity;
import com.sqles.demo.domain.TestEntityExample;
import com.sqles.demo.mapper.TestEntityMapper;
import com.sqles.demo.schema.TestEntityIndexMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;


/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@SpringBootTest(classes = SqlEsDemoApplication.class )
public class TestMybatisIndex {
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
    public void testInsert() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));
        Assertions.assertEquals(1, c1);

        testEntityIndexMapper.refreshIndex();
        TestEntity testEntity = testEntityMapper.selectByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals(testEntity.getKeywordField(), list.get(0).getKeywordField());


        int c2 = testEntityMapper.insert(list.get(1));
        Assertions.assertEquals(1, c2);

        testEntityIndexMapper.refreshIndex();
        testEntity = testEntityMapper.selectByPrimaryKey(list.get(1).getId());
        Assertions.assertEquals(testEntity.getKeywordField(), list.get(1).getKeywordField());
    }

    @Test
    public void testInsertSelective() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insertSelective(list.get(0));
        Assertions.assertEquals(1, c1);

        testEntityIndexMapper.refreshIndex();
        TestEntity testEntity = testEntityMapper.selectByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals(testEntity.getKeywordField(), list.get(0).getKeywordField());


        int c2 = testEntityMapper.insertSelective(list.get(1));
        Assertions.assertEquals(1, c2);

        testEntityIndexMapper.refreshIndex();
        testEntity = testEntityMapper.selectByPrimaryKey(list.get(1).getId());
        Assertions.assertEquals(testEntity.getKeywordField(), list.get(1).getKeywordField());
    }

    @Test
    public void testDelete() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));

        testEntityIndexMapper.refreshIndex();
        TestEntityExample testEntityExample = new TestEntityExample();
        testEntityExample.or().andIdEqualTo(list.get(0).getId()).andDateFieldEqualTo(list.get(0).getDateField());
        int count = testEntityMapper.deleteByExample(testEntityExample);
        Assertions.assertEquals(1, count);
    }

    @Test
    public void testDeleteById() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));

        testEntityIndexMapper.refreshIndex();
        int count = testEntityMapper.deleteByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals(1, count);
    }

    @Test
    public void testUpdate() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));
        testEntityIndexMapper.refreshIndex();
        TestEntity updateEntity = list.get(0);
        updateEntity.setKeywordField("testUpdate");

        TestEntityExample testEntityExample = new TestEntityExample();
        testEntityExample.or().andIdEqualTo(list.get(0).getId()).andDateFieldEqualTo(list.get(0).getDateField());
        int count = testEntityMapper.updateByExample(updateEntity, testEntityExample);

        testEntityIndexMapper.refreshIndex();
        TestEntity testEntity = testEntityMapper.selectByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals("testUpdate", testEntity.getKeywordField());
    }

    @Test
    public void testUpdateByExampleSelective() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));
        testEntityIndexMapper.refreshIndex();
        TestEntity updateEntity = new TestEntity();
        updateEntity.setKeywordField("testUpdateByExampleSelective");

        TestEntityExample testEntityExample = new TestEntityExample();
        testEntityExample.or().andIdEqualTo(list.get(0).getId()).andDateFieldEqualTo(list.get(0).getDateField());
        int count = testEntityMapper.updateByExampleSelective(updateEntity, testEntityExample);

        testEntityIndexMapper.refreshIndex();
        TestEntity testEntity = testEntityMapper.selectByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals("testUpdateByExampleSelective", testEntity.getKeywordField());
    }

    @Test
    public void testUpdateByPrimaryKey() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));
        testEntityIndexMapper.refreshIndex();
        TestEntity updateEntity = list.get(0);
        updateEntity.setKeywordField("testUpdateByPrimaryKey");

        int count = testEntityMapper.updateByPrimaryKey(updateEntity);

        testEntityIndexMapper.refreshIndex();
        TestEntity testEntity = testEntityMapper.selectByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals("testUpdateByPrimaryKey", testEntity.getKeywordField());
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        List<TestEntity> list = TestUtil.createList(2, 21);
        int c1 = testEntityMapper.insert(list.get(0));
        testEntityIndexMapper.refreshIndex();
        TestEntity updateEntity = list.get(0);
        updateEntity.setKeywordField("testUpdateByPrimaryKeySelective");

        int count = testEntityMapper.updateByPrimaryKeySelective(updateEntity);

        testEntityIndexMapper.refreshIndex();
        TestEntity testEntity = testEntityMapper.selectByPrimaryKey(list.get(0).getId());
        Assertions.assertEquals("testUpdateByPrimaryKeySelective", testEntity.getKeywordField());
    }

    @Test
    public void testSelectByExample() {
        TestEntityExample testEntityExample = new TestEntityExample();
        testEntityExample.or().andLongFieldBetween(1L, 3L);
        List<TestEntity> result = testEntityMapper.selectByExample(testEntityExample);
        Assertions.assertEquals(3, result.size());

        HashSet<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        set.add(3L);

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }

    @Test
    public void testSelectByExample2() {
        TestEntityExample testEntityExample = new TestEntityExample();
        testEntityExample.or().andLongFieldBetween(1L, 3L);
        List<TestEntity> result = testEntityMapper.selectByExample(testEntityExample);
        Assertions.assertEquals(3, result.size());

        HashSet<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        set.add(3L);

        for (TestEntity testEntity : result) {
            Assertions.assertTrue(set.contains(testEntity.getLongField()));
            set.remove(testEntity.getLongField());
        }

        Assertions.assertEquals(0, set.size());
    }
}
