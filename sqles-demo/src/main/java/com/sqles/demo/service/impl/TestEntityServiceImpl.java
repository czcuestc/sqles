package com.sqles.demo.service.impl;

import com.sqles.demo.domain.TestEntity;
import com.sqles.demo.mapper.TestEntityMapper;
import com.sqles.demo.schema.TestEntityIndexMapper;
import com.sqles.demo.service.TestEntityService;
import com.sqles.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Service
public class TestEntityServiceImpl implements TestEntityService {
    @Autowired
    private TestEntityMapper testEntityMapper;

    @Autowired
    private TestEntityIndexMapper testEntityIndexMapper;

    @Override
    public boolean createIndex() {
        if(testEntityIndexMapper.existIndex()) {
            return true;
        }

        boolean result = testEntityIndexMapper.createIndex();
        if (result) {
            List<TestEntity> list = Util.createList(100, 0);
            int size = testEntityIndexMapper.bulkInsert(list);
        }

        return result;
    }

    @Override
    public List<TestEntity> queryIndex() {
       return null;
    }

    @Override
    public int insert(TestEntity testEntity) {
        return testEntityMapper.insert(testEntity);
    }

    @Override
    public int update(TestEntity testEntity) {
        return testEntityMapper.updateByPrimaryKey(testEntity);
    }
}
