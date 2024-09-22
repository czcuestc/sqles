package com.czcuestc.sqles.meta;

import java.io.Serializable;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class IndexInfo implements Serializable {
    private static final long serialVersionUID = -7135605784045647755L;
    private String indexName;

    private String aliasName;

    private EntityInfo entityInfo;

    private Short shards;

    private Short replicas;

    private Integer maxResultWindow;

    private String refreshInterval;

    private boolean setting;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public EntityInfo getEntityInfo() {
        return entityInfo;
    }

    public void setEntityInfo(EntityInfo entityInfo) {
        this.entityInfo = entityInfo;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Short getShards() {
        return shards;
    }

    public void setShards(Short shards) {
        this.shards = shards;
    }

    public Short getReplicas() {
        return replicas;
    }

    public void setReplicas(Short replicas) {
        this.replicas = replicas;
    }

    public Integer getMaxResultWindow() {
        return maxResultWindow;
    }

    public void setMaxResultWindow(Integer maxResultWindow) {
        this.maxResultWindow = maxResultWindow;
    }

    public String getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(String refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public boolean isSetting() {
        return setting;
    }

    public void setSetting(boolean setting) {
        this.setting = setting;
    }
}
