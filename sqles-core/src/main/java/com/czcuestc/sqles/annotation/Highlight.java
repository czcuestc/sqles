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
@Target(ElementType.FIELD)
public @interface Highlight {
    String preTag() default IndexConstants.PRE_TAG;

    String postTag() default IndexConstants.POST_TAG;

    int fragmentSize() default 128;

    int fragmentNumber() default -1;
}
