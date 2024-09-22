package com.czcuestc.sqles.meta.container;

import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.meta.IndexInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class IndexInfoManager {
    private static IndexInfoManager instance = new IndexInfoManager();

    private Map<String, IndexInfo> infoMap = new ConcurrentHashMap<>();

    private IndexInfoManager() {
    }

    public static IndexInfoManager getInstance() {
        return instance;
    }

    public void put(IndexInfo indexInfo) {
        if (infoMap.containsKey(indexInfo.getIndexName())) {
            throw new RuntimeException(StringUtil.format("create duplicate index {}", indexInfo.getIndexName()));
        }
        infoMap.put(indexInfo.getIndexName(), indexInfo);
    }

    public IndexInfo getIndexInfo(String indexName) {
        return infoMap.get(indexName);
    }

    public Boolean containsKey(String indexName) {
        return infoMap.containsKey(indexName);
    }
}
