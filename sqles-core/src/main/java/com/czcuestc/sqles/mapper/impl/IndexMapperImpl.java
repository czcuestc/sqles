package com.czcuestc.sqles.mapper.impl;

import com.czcuestc.sqles.mapper.IndexMapper;
import com.czcuestc.sqles.util.IndexUtil;
import org.elasticsearch.cluster.metadata.MappingMetadata;

import java.util.List;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class IndexMapperImpl<T> implements IndexMapper<T> {
    private String datasource;

    private Class<T> entityClazz;

    public IndexMapperImpl(String datasource, Class<T> clazz) {
        init(datasource, clazz);
    }

    private void init(String datasource, Class<T> clazz) {
        this.datasource = datasource;
        this.entityClazz = clazz;
    }

    @Override
    public String getDatasource() {
        return datasource;
    }

    @Override
    public Class<T> getEntityClazz() {
        return entityClazz;
    }

    @Override
    public Map<String, MappingMetadata> getIndexMapping() {
        return IndexUtil.getIndexMeta(datasource, entityClazz);
    }

    @Override
    public boolean createIndex() {
        return IndexUtil.createIndex(datasource, entityClazz);
    }

    @Override
    public boolean existIndex() {
        return IndexUtil.existIndex(datasource, entityClazz);
    }

    @Override
    public boolean deleteIndex() {
        return IndexUtil.deleteIndex(datasource, entityClazz);
    }

    @Override
    public boolean refreshIndex() {
        return IndexUtil.refreshIndex(datasource, entityClazz);
    }

    @Override
    public long count() {
        return IndexUtil.count(datasource, entityClazz);
    }

    @Override
    public int insert(T entity) {
        return IndexUtil.insert(datasource, entityClazz, entity);
    }

    @Override
    public int bulkInsert(List<T> objects) {
        return IndexUtil.bulkInsert(datasource, entityClazz, objects);
    }

    @Override
    public T findById(Object id) {
        return (T) IndexUtil.findById(datasource, entityClazz, id);
    }

    @Override
    public List<T> findByIds(List ids) {
        return IndexUtil.findByIds(datasource, entityClazz, ids);
    }

    @Override
    public boolean contains(Object id) {
        return IndexUtil.exist(datasource, entityClazz, id);
    }

    @Override
    public int deleteById(Object id) {
        return IndexUtil.deleteById(datasource, entityClazz, id);
    }

    @Override
    public int bulkDelete(List ids) {
        return IndexUtil.deleteByIds(datasource, entityClazz, ids);
    }

    @Override
    public int update(T entity) {
        return IndexUtil.update(datasource, entityClazz, entity);
    }

    @Override
    public int bulkUpdate(List<T> entities) {
        return IndexUtil.bulkUpdate(datasource, entityClazz, entities);
    }
}

