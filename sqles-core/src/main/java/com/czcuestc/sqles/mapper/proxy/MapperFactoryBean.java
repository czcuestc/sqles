package com.czcuestc.sqles.mapper.proxy;

import com.czcuestc.sqles.mapper.IndexMapper;
import com.czcuestc.sqles.mapper.container.MapperManager;
import com.czcuestc.sqles.mapper.impl.IndexMapperImpl;
import com.czcuestc.sqles.util.Util;
import org.springframework.beans.factory.FactoryBean;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class MapperFactoryBean<T extends IndexMapper> implements FactoryBean<T> {
    private Class<?> mapperClazz;

    private String datasource;

    public MapperFactoryBean(Class<T> mapperClazz, String datasource) {
        this.mapperClazz = mapperClazz;
        this.datasource = datasource;
    }

    @Override
    public T getObject() {
        init();
        MapperHandler mapperHandler = new MapperHandler(this.mapperClazz);
        IndexMapper mapper = (IndexMapper) mapperHandler.getProxy();
        return (T) mapper;
    }

    private synchronized void init() {
        Class<?> clazz = Util.getEntityClazz(this.mapperClazz);
        IndexMapper indexMapper = new IndexMapperImpl(datasource, clazz);
        MapperManager.getInstance().putMapper(this.mapperClazz, indexMapper);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperClazz;
    }
}
