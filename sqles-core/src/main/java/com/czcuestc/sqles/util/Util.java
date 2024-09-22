package com.czcuestc.sqles.util;

import com.czcuestc.sqles.common.util.StringUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Util {
    public static Class getEntityClazz(Class<?> clazz) {
        Type[] genericSuperclass = clazz.getGenericInterfaces();
        Type type = genericSuperclass[0];
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0 && actualTypeArguments[0] instanceof Class) {
                return (Class<?>) actualTypeArguments[0];
            }
        }

        throw new RuntimeException(StringUtil.format("{} not find matched entity clazz", clazz));
    }
}
