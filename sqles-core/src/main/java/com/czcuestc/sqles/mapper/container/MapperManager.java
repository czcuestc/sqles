package com.czcuestc.sqles.mapper.container;

import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.mapper.IndexMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class MapperManager {
    private static MapperManager instance = new MapperManager();

    private Map<Class<?>, IndexMapper> mappers = new ConcurrentHashMap<>();

    private MapperManager() {
    }

    public static MapperManager getInstance() {
        return instance;
    }

    public IndexMapper getMapper(Class<?> clazz) {
        return mappers.get(clazz);
    }

    public void putMapper(Class<?> mapperClazz,IndexMapper indexMapper) {
        if (mappers.containsKey(mapperClazz)) {
            throw new RuntimeException(StringUtil.format("{} duplicate mapper", indexMapper.getEntityClazz()));
        }

        mappers.put(mapperClazz, indexMapper);
    }

    public int size() {
        return mappers.size();
    }
}
