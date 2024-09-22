package com.sqles.demo.service;

import com.sqles.demo.domain.TestEntity;

import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public interface TestEntityService {
    boolean createIndex();

    List<TestEntity> queryIndex();

    int insert(TestEntity testEntity);

    int update(TestEntity testEntity);
}
