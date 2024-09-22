package com.czcuestc.sqles.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = FIELD)
public @interface Id {
}
