package com.czcuestc.sqles.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LongField {
    boolean index() default true;

    boolean store() default false;

    String nullValue() default "";

    boolean docValues() default true;
}