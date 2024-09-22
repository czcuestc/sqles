package com.czcuestc.sqles.mapper;

import org.elasticsearch.cluster.metadata.MappingMetadata;

import java.util.List;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public interface IndexMapper<T> {
    String getDatasource();

    Class<T> getEntityClazz();

    /**
     * 索引mapping元数据
     * @return
     */
    Map<String, MappingMetadata> getIndexMapping();

    /**
     * 创建索引，索引名为实体类名
     *
     * @return
     */
    boolean createIndex();

    /**
     * 索引是否存在
     * @return
     */
    boolean existIndex();

    /**
     * 删除索引
     * @return
     */
    boolean deleteIndex();

    /**
     * 刷新索引，若部分shard失败，也返回false,全部成功才返回true
     *
     * @return
     */
    boolean refreshIndex();

    /**
     * 索引总记录数
     * @return
     */
    long count();

    /**
     * 若entity id未设置，则失败返回异常
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 批量插入，若entity id未设置，则失败返回异常
     *
     * @param entities
     * @return
     */
    int bulkInsert(List<T> entities);

    /**
     * 按id查找记录
     * @param id
     * @return
     */
    T findById(Object id);

    /**
     * 按ids查找记录
     * @param ids
     * @return
     */
    List<T> findByIds(List ids);

    /**
     * 按id检查是否存在记录
     * @param id
     * @return
     */
    boolean contains(Object id);

    /**
     * 按id删除记录
     * @param id
     * @return
     */
    int deleteById(Object id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int bulkDelete(List ids);

    /**
     * 按id字段更新，若entity id未设置，则失败返回异常
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 批量更新，按id字段更新，若entity id未设置，则失败返回异常
     *
     * @param entities
     * @return
     */
    int bulkUpdate(List<T> entities);
}
