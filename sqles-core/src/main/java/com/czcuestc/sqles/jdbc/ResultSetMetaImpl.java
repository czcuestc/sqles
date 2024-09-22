package com.czcuestc.sqles.jdbc;

import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.domain.Header;
import com.czcuestc.sqles.engine.domain.Result;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ResultSetMetaImpl implements ResultSetMetaData {
    private Result result;
    private List<Header> headers;

    public ResultSetMetaImpl(Result result) {
        this.result = result;
        this.headers = result.getHeaders();
    }

    private int getOffset() {
        return -1;
    }

    private int getIndex(int index) {
        return index + getOffset();
    }

    private void checkColumnBounds(int columnIndex) throws SQLException {
        if (columnIndex < 1 || columnIndex > this.headers.size()) {
            throw SQLError.createSQLException(StringUtil.concat("columnIndex out of bound", columnIndex));
        }
    }

    @Override
    public int getColumnCount() throws SQLException {
        return result.sizeOfColumn();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        checkColumnBounds(column);
        int index = getIndex(column);
        return headers.get(index).name;
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        checkColumnBounds(column);
        int index = getIndex(column);
        return headers.get(index).name;
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public String getTableName(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        checkColumnBounds(column);

        int index = getIndex(column);
        Header header = this.headers.get(index);
        switch (header.dataType) {
            case BOOL:
                return Types.BOOLEAN;
            case BYTE:
                return Types.TINYINT;
            case INT16:
                return Types.SMALLINT;
            case INT32:
                return Types.INTEGER;
            case INT64:
                return Types.BIGINT;
            case FLOAT:
                return Types.FLOAT;
            case DOUBLE:
                return Types.DOUBLE;
            case DECIMAL:
                return Types.DECIMAL;
            case STRING:
                return Types.VARCHAR;
            case TIME:
                return Types.TIME;
            case DATE:
                return Types.DATE;
            case TIMESTAMP:
                return Types.TIMESTAMP;
        }
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return true;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        checkColumnBounds(column);

        int index = getIndex(column);
        Header header = this.headers.get(index);
        switch (header.dataType) {
            case BOOL:
                return Boolean.class.getName();
            case BYTE:
                return Byte.class.getName();
            case INT16:
                return Short.class.getName();
            case INT32:
                return Integer.class.getName();
            case INT64:
                return Long.class.getName();
            case FLOAT:
                return Float.class.getName();
            case DOUBLE:
                return Double.class.getName();
            case DECIMAL:
                return BigDecimal.class.getName();
            case STRING:
                return String.class.getName();
            case TIME:
                return Time.class.getName();
            case DATE:
                return Date.class.getName();
            case TIMESTAMP:
                return Timestamp.class.getName();
        }
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }
}
