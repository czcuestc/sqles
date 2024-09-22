package com.czcuestc.sqles.jdbc;

import com.czcuestc.sqles.common.util.StringUtil;

import java.sql.SQLException;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class SQLError {
    public static SQLException createSQLException(String message) {
        return new SQLException(message);
    }

    public static SQLException createSQLException(Throwable e) {
        return new SQLException(e);
    }

    public static SQLException createSQLFeatureNotSupportedException() {
        return new SQLException("not supported");
    }

    public static SQLException createSQLFeatureNotSupportedException(Object message) {
        return new SQLException(StringUtil.format("not support {}", String.valueOf(message)));
    }
}
