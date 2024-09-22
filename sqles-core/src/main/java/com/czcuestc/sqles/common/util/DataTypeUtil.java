package com.czcuestc.sqles.common.util;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.exceptions.NotSupportException;
import com.czcuestc.sqles.common.exceptions.SystemException;
import com.czcuestc.sqles.meta.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class DataTypeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTypeUtil.class);

    public static DataType toDataType(String typeName) {
        switch (typeName) {
            case "boolean":
            case "Boolean": {
                return DataType.BOOL;
            }
            case "byte":
            case "Byte": {
                return DataType.BYTE;
            }
            case "short":
            case "Short": {
                return DataType.INT16;
            }
            case "int":
            case "Integer": {
                return DataType.INT32;
            }
            case "long":
            case "Long": {
                return DataType.INT64;
            }
            case "float":
            case "Float": {
                return DataType.FLOAT;
            }
            case "double":
            case "Double": {
                return DataType.DOUBLE;
            }
            case "BigDecimal": {
                return DataType.DECIMAL;
            }
            case "LocalTime":
            case "Time": {
                return DataType.TIME;
            }
            case "LocalDate":
            case "Date": {
                return DataType.DATE;
            }
            case "LocalDateTime":
            case "Instant":
            case "Timestamp": {
                return DataType.TIMESTAMP;
            }
            case "String": {
                return DataType.STRING;
            }
            default:
                break;
        }

        throw new NotSupportException(typeName);
    }

    public static DataType toDataType(Class<?> clazz) {
        switch (clazz.getSimpleName()) {
            case "boolean":
            case "Boolean": {
                return DataType.BOOL;
            }
            case "byte":
            case "Byte": {
                return DataType.BYTE;
            }
            case "short":
            case "Short": {
                return DataType.INT16;
            }
            case "int":
            case "Integer": {
                return DataType.INT32;
            }
            case "long":
            case "Long": {
                return DataType.INT64;
            }
            case "float":
            case "Float": {
                return DataType.FLOAT;
            }
            case "double":
            case "Double": {
                return DataType.DOUBLE;
            }
            case "BigDecimal": {
                return DataType.DECIMAL;
            }
            case "LocalTime":
            case "Time": {
                return DataType.TIME;
            }
            case "LocalDate":
            case "Date": {
                if (clazz == java.util.Date.class) {
                    return DataType.TIMESTAMP;
                } else {
                    return DataType.DATE;
                }
            }
            case "LocalDateTime":
            case "Instant":
            case "Timestamp": {
                return DataType.TIMESTAMP;
            }
            case "String": {
                return DataType.STRING;
            }
            default:
                break;
        }

        throw new NotSupportException(clazz.getSimpleName());
    }

    public static FieldType getFieldType(DataType dataType) {
        switch (dataType) {
            case BOOL:
                return FieldType.BOOLEAN;
            case BYTE:
                return FieldType.BYTE;
            case INT16:
                return FieldType.SHORT;
            case INT32:
                return FieldType.INTEGER;
            case INT64:
                return FieldType.LONG;
            case FLOAT:
                return FieldType.FLOAT;
            case DOUBLE:
                return FieldType.DOUBLE;
            case DECIMAL:
                return FieldType.SCALED_FLOAT;
            case STRING:
                return FieldType.TEXT;
            case TIME:
            case DATE:
                return FieldType.DATE;
            default:
                return FieldType.KEYWORD;
        }
    }

    public static Number toNumber(Object value, DataType dataType) {
        if (value == null)
            return null;

        if (DataType.isNumber(dataType)) {
            return (Number) value;
        } else {
            try {
                return new BigDecimal(String.valueOf(value));
            } catch (Exception e) {
                throw new NotSupportException(StringUtil.format("unable to convert {} to number", value));
            }
        }
    }

    public static Integer toInteger(Object value, DataType dataType) {
        if (value == null)
            return null;

        switch (dataType) {
            case BYTE:
                return ((Byte) value).intValue();
            case INT16:
                return ((Short) value).intValue();
            case INT32:
                return ((Integer) value).intValue();
            case INT64: {
                Long v = ((Long) value);
                if (v.longValue() > Integer.MAX_VALUE || v.longValue() < Integer.MIN_VALUE) {
                    throw new NotSupportException(StringUtil.format("unable to convert {} to int", value));
                } else {
                    return v.intValue();
                }
            }
            default: {
                throw new NotSupportException(StringUtil.format("unable to convert {} to int", value));
            }
        }
    }

    public static Long toLong(Object value, DataType dataType) {
        if (value == null)
            return null;

        switch (dataType) {
            case BYTE:
                return ((Byte) value).longValue();
            case INT16:
                return ((Short) value).longValue();
            case INT32:
                return ((Integer) value).longValue();
            case INT64:
                return (Long) value;
            default: {
                throw new NotSupportException(StringUtil.format("unable to convert {} to long", value));
            }
        }
    }

    public static BigDecimal toDecimal(Object value, DataType dataType) {
        if (value == null)
            return null;

        if (dataType == null) {
            return new BigDecimal(String.valueOf(value));
        }

        switch (dataType) {
            case BYTE:
                return new BigDecimal((Byte) value);
            case INT16:
                return new BigDecimal((Short) value);
            case INT32:
                return new BigDecimal((Integer) value);
            case LONG_TIME:
            case LONG_DATE:
            case LONG_TIMESTAMP:
            case INT64:
                return new BigDecimal((Long) value);
            case FLOAT:
                return new BigDecimal((Float) value);
            case DOUBLE:
                return new BigDecimal((Double) value);
            case DECIMAL:
                return (BigDecimal) value;
            default:
                return new BigDecimal(String.valueOf(value));
        }
    }

    public static Object convert(DataType type, Object value) {
        if (value == null)
            return null;

        if (type == null)
            return value;

        switch (type) {
            case BOOL:
                return Boolean.valueOf(String.valueOf(value));
            case BYTE:
                return Byte.valueOf(String.valueOf(value));
            case INT16:
                return Short.valueOf(String.valueOf(value));
            case INT32:
                return Integer.valueOf(String.valueOf(value));
            case LONG_TIME:
            case LONG_DATE:
            case LONG_TIMESTAMP:
            case INT64:
                return Long.valueOf(String.valueOf(value));
            case FLOAT:
                return Float.valueOf(String.valueOf(value));
            case DOUBLE:
                return Double.valueOf(String.valueOf(value));
            case DECIMAL:
                return new BigDecimal(String.valueOf(value));
            case STRING:
                return String.valueOf(value);
            case TIME:
                return Time.valueOf(String.valueOf(value));
            case DATE:
                return Date.valueOf(String.valueOf(value));
            case TIMESTAMP:
                return Timestamp.valueOf(String.valueOf(value));
            default:
                throw new SystemException("not support type!");
        }
    }

    public static Object convert(DataType dataType, Object value, DataType toDataType) {
        if (value == null) {
            return null;
        }

        if (dataType == toDataType) {
            return value;
        }

        if (DataType.isNumber(dataType) && DataType.isNumber(toDataType)) {
            if (toDataType.getIndex() < dataType.getIndex()) {
                LOGGER.error("{} fail to convert from {} to {}", value, dataType, toDataType);
                throw new SystemException(StringUtil.format("{} fail to convert from {} to {}", value, dataType, toDataType));
            }
            Number number = (Number) value;
            switch (toDataType) {
                case BYTE: {
                    return number.byteValue();
                }
                case INT16: {
                    return number.shortValue();
                }
                case INT32: {
                    return number.intValue();
                }
                case INT64:
                case LONG_TIME:
                case LONG_DATE:
                case LONG_TIMESTAMP: {
                    return number.longValue();
                }
                case FLOAT: {
                    return number.floatValue();
                }
                case DOUBLE: {
                    return number.doubleValue();
                }
            }
        }

        return convert(toDataType, value);
    }
}
