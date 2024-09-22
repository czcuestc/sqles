package com.czcuestc.sqles.annotation;

import java.lang.annotation.*;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Document {
    String value() default "";

    /**
     * 是否开启souce存储，若为false,则source被禁用
     * @return
     */
    boolean source() default true;
}
