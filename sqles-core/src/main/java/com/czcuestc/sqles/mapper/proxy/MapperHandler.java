package com.czcuestc.sqles.mapper.proxy;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */

import com.czcuestc.sqles.mapper.IndexMapper;
import com.czcuestc.sqles.mapper.container.MapperManager;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MapperHandler implements InvocationHandler, Serializable {
    private Class<?> mapperClazz;

    private IndexMapper indexMapper;


    public MapperHandler(Class<?> mapperClazz) {
        this.mapperClazz = mapperClazz;
        this.indexMapper = MapperManager.getInstance().getMapper(mapperClazz);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(indexMapper, args);
        return result;
    }

    // 获取代理对象
    public Object getProxy() {
        return Proxy.newProxyInstance(
                mapperClazz.getClassLoader(),
                new Class[]{mapperClazz},
                this
        );
    }
}
