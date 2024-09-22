package com.czcuestc.sqles.meta;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public enum FieldType {
    AUTO(0, "auto"),
    TEXT(1, "text"),
    KEYWORD(2, "keyword"),
    BOOLEAN(3, "boolean"),
    BYTE(4, "byte"),
    SHORT(5, "short"),
    INTEGER(6, "integer"),
    LONG(7, "long"),
    FLOAT(8, "float"),
    HALF_FLOAT(9, "half_float"),
    SCALED_FLOAT(10, "scaled_float"),
    DOUBLE(11, "double"),
    INTEGER_RANGE(12, "integer_range"),
    FLOAT_RANGE(13, "float_range"),
    LONG_RANGE(14, "long_range"),
    DOUBLE_RANGE(15, "double_range"),
    DATE(16, "date"),
    DATE_NANOS(17, "date_nanos"),
    DATE_RANGE(18, "date_range"),
    IP(19, "ip"), //
    IP_RANGE(20, "ip_range"),
    BINARY(21, "binary"),
    WILDCARD(23, "wildcard"),
    GEO_POINT(24, "geo_point"),
    GEO_SHAPE(25, "geo_shape"),
    Object(26, "object");

    private int index;
    private String fieldType;

    FieldType(int index, String fieldType) {
        this.index = index;
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public int getIndex() {
        return index;
    }

    public boolean isUnknown() {
        return this.index == 0;
    }
}