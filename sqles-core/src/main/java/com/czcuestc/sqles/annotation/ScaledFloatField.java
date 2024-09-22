package com.czcuestc.sqles.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScaledFloatField {
    String value() default "";
    boolean index() default true;
    boolean store() default false;
    String nullValue() default "";
    boolean docValues() default true;
    double scalingFactor() default 1;
}
