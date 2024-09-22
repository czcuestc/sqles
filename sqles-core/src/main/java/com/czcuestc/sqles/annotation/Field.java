package com.czcuestc.sqles.annotation;

import com.czcuestc.sqles.meta.FieldType;

import java.lang.annotation.*;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field {
    FieldType type() default FieldType.AUTO;

    boolean index() default true;

    boolean store() default false;

    boolean docValues() default true;

    boolean norms() default true;

    String searchAnalyzer() default "";

    String analyzer() default "";

    String nullValue() default "";

    String dateFormat() default "";

    int ignoreAbove() default -1;

    double scalingFactor() default 1;

    IndexOptions indexOptions() default IndexOptions.NONE;
}
