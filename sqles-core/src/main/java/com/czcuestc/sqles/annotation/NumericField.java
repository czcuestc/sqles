package com.czcuestc.sqles.annotation;

import com.czcuestc.sqles.meta.FieldType;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public @interface NumericField {
    FieldType type() default FieldType.AUTO;

    boolean index() default true;

    boolean store() default false;

    String nullValue() default "";

    boolean docValues() default false;
}
