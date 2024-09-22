package com.czcuestc.sqles.common;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public enum DataType {
    AUTO(0, "AUTO"),
    BOOL(11, "BOOL"),
    BYTE(12, "BYTE"),
    INT16(13, "INT16"),
    INT32(14, "INT32"),
    INT64(15, "INT64"),
    LONG_TIME(16, "LONG_TIME"),
    LONG_DATE(20, "LONG_DATE"),
    LONG_TIMESTAMP(24, "LONG_TIMESTAMP"),
    FLOAT(40, "FLOAT"),
    DOUBLE(44, "DOUBLE"),
    DECIMAL(48, "DECIMAL"),
    TIME(52, "TIME"),
    DATE(56, "DATE"),
    TIMESTAMP(60, "TIMESTAMP"),
    STRING(66, "STRING");

    private int index;
    private String name;

    DataType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public String getType() {
        return name;
    }

    public static DataType getType(int index) {
        switch (index) {
            case 11:
                return BOOL;
            case 12:
                return BYTE;
            case 13:
                return INT16;
            case 14:
                return INT32;
            case 15:
                return INT64;
            case 16:
                return LONG_TIME;
            case 20:
                return LONG_DATE;
            case 24:
                return LONG_TIMESTAMP;
            case 40:
                return FLOAT;
            case 44:
                return DOUBLE;
            case 48:
                return DECIMAL;
            case 52:
                return TIME;
            case 56:
                return DATE;
            case 60:
                return TIMESTAMP;
            case 66:
                return STRING;
            default:
                return AUTO;
        }
    }

    public static boolean isInteger(DataType type) {
        return (type.getIndex() >= DataType.BYTE.getIndex() && type.getIndex() <= DataType.LONG_TIMESTAMP.getIndex());
    }

    public static boolean isNumberExcludeDecimal(DataType type) {
        return (type.getIndex() >= DataType.BYTE.getIndex() && type.getIndex() <= DataType.DOUBLE.getIndex());
    }

    public static boolean isNumber(DataType type) {
        return (type.getIndex() >= DataType.BYTE.getIndex() && type.getIndex() <= DataType.DECIMAL.getIndex());
    }

    public static boolean isFloat(DataType type) {
        return (type.getIndex() >= DataType.FLOAT.getIndex() && type.getIndex() <= DataType.DOUBLE.getIndex());
    }

    public static boolean isTime(DataType type) {
        return (type.getIndex() >= DataType.TIME.getIndex() && type.getIndex() <= DataType.TIMESTAMP.getIndex());
    }

    public static boolean isLongTime(DataType type) {
        return (type.getIndex() >= DataType.LONG_TIME.getIndex() && type.getIndex() <= DataType.LONG_TIMESTAMP.getIndex());
    }

    public static boolean isString(DataType type) {
        return type == DataType.STRING;
    }

    public static boolean isDecimal(DataType type) {
        return type == DataType.DECIMAL;
    }

    public static boolean isBool(DataType type) {
        return type == DataType.BOOL;
    }

    public static boolean isUnknown(DataType type) {
        return type == DataType.AUTO;
    }

    public static boolean greaterThanEquals(DataType type1, DataType type2) {
        return type1.getIndex() >= type2.getIndex();
    }
}
