package com.czcuestc.sqles.meta.container;

import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.meta.EntityInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EntityInfoManager {
    private static EntityInfoManager instance = new EntityInfoManager();

    private Map<Class, EntityInfo> infoMap = new ConcurrentHashMap<>();

    private EntityInfoManager() {
    }

    public static EntityInfoManager getInstance() {
        return instance;
    }

    public void put(EntityInfo entityInfo) {
        if (infoMap.containsKey(entityInfo.getClazz())) {
            throw new RuntimeException(StringUtil.format("create duplicate entity {}", entityInfo.getClazz()));
        }
        infoMap.put(entityInfo.getClazz(), entityInfo);
    }

    public EntityInfo get(Class<?> clazz) {
        return infoMap.get(clazz);
    }

    public Boolean containsKey(Class clazz) {
        return infoMap.containsKey(clazz);
    }
}
