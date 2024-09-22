package com.czcuestc.sqles.annotation;

import com.czcuestc.sqles.meta.FieldType;

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
public @interface RangeField {
    FieldType type() default FieldType.AUTO;

    boolean index() default true;

    boolean store() default true;
}
