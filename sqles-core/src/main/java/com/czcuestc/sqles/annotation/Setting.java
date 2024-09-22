package com.czcuestc.sqles.annotation;

import com.czcuestc.sqles.util.IndexConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Setting {
    short shards() default 1;

    short replicas() default 1;

    int maxResultWindow() default IndexConstants.MAX_RESULT_WINDOW;

    String refreshInterval() default "";
}