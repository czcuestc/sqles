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
}
