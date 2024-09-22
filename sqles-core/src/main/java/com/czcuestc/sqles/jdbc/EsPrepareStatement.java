package com.czcuestc.sqles.jdbc;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.DateUtil;
import com.czcuestc.sqles.engine.context.Parameters;
import com.czcuestc.sqles.engine.domain.SqlResult;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EsPrepareStatement implements PreparedStatement {
    private Parameters parameters;

    protected String originalSql;

    private ResultSet resultSet;

    private SqlResult sqlResult;

    private EsConnection connection;

    private AtomicBoolean closed = new AtomicBoolean(false);

    public EsPrepareStatement() {
    }

    public EsPrepareStatement(String sql) {
        this.originalSql = sql;
        this.parameters = new Parameters();
    }

    public EsPrepareStatement(EsConnection connection, String sql) {
        this.connection = connection;
        this.originalSql = sql;
        this.parameters = new Parameters();
    }

    private int getOffset() {
        return -1;
    }

    private int getIndex(int index) {
        return index + getOffset();
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        try {
            sqlResult = connection.getExecutor().execute(originalSql, parameters);
            this.resultSet = new ResultSetImpl(sqlResult.getResult(), this);
            return this.resultSet;
        } catch (Exception e) {
            throw SQLError.createSQLException(e);
        }
    }

    @Override
    public boolean execute() throws SQLException {
        executeQuery();
        return true;
    }

    @Override
    public int executeUpdate() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.AUTO);
        this.parameters.setParameter(index, null);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.BOOL);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.BYTE);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.INT16);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.INT32);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.INT64);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.FLOAT);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.DOUBLE);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.DECIMAL);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        int index = getIndex(parameterIndex);

        this.parameters.setType(index, DataType.STRING);
        this.parameters.setParameter(index, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.LONG_DATE);
        this.parameters.setParameter(index, x.getTime());
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.LONG_TIME);
        this.parameters.setParameter(index, x.getTime());
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        int index = getIndex(parameterIndex);
        this.parameters.setType(index, DataType.LONG_TIMESTAMP);
        this.parameters.setParameter(index, x.getTime());
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void clearParameters() throws SQLException {
        this.parameters.clear();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setObject(int parameterIndex, Object parameterObj) throws SQLException {
        if (parameterObj == null) {
            this.setNull(parameterIndex, 0);
        } else if (parameterObj instanceof Byte) {
            this.setByte(parameterIndex, ((Byte) parameterObj).byteValue());
        } else if (parameterObj instanceof String) {
            this.setString(parameterIndex, (String) parameterObj);
        } else if (parameterObj instanceof BigDecimal) {
            this.setBigDecimal(parameterIndex, (BigDecimal) parameterObj);
        } else if (parameterObj instanceof Short) {
            this.setShort(parameterIndex, (Short) parameterObj);
        } else if (parameterObj instanceof Integer) {
            this.setInt(parameterIndex, (Integer) parameterObj);
        } else if (parameterObj instanceof Long) {
            this.setLong(parameterIndex, (Long) parameterObj);
        } else if (parameterObj instanceof Float) {
            this.setFloat(parameterIndex, (Float) parameterObj);
        } else if (parameterObj instanceof Double) {
            this.setDouble(parameterIndex, (Double) parameterObj);
        } else if (parameterObj instanceof Date) {
            this.setDate(parameterIndex, (Date) parameterObj);
        } else if (parameterObj instanceof Time) {
            this.setTime(parameterIndex, (Time) parameterObj);
        } else if (parameterObj instanceof Timestamp) {
            this.setTimestamp(parameterIndex, (Timestamp) parameterObj);
        } else if (parameterObj instanceof Boolean) {
            this.setBoolean(parameterIndex, (Boolean) parameterObj);
        } else if (parameterObj instanceof LocalTime) {
            this.setLong(parameterIndex, DateUtil.epoch((LocalTime) parameterObj));
        } else if (parameterObj instanceof LocalDate) {
            this.setLong(parameterIndex, DateUtil.epoch((LocalDate) parameterObj));
        } else if (parameterObj instanceof LocalDateTime) {
            this.setLong(parameterIndex, DateUtil.epoch((LocalDateTime) parameterObj));
        } else if (parameterObj instanceof Instant) {
            this.setLong(parameterIndex, DateUtil.epoch((Instant) parameterObj));
        } else {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
    }

    @Override
    public void addBatch() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.resultSet.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            SqlResult sqlResult = connection.getExecutor().execute(originalSql);
//            Result result = SQLUtil.executeQuery(originalSql, this.parameters);
            this.resultSet = new ResultSetImpl(sqlResult.getResult(), this);
            return this.resultSet;
        } catch (Exception e) {
            throw SQLError.createSQLException(e);
        }
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void close() throws SQLException {
        if (!isClosed()) {
//            if (connection != null) {
//                connection.close();
//            }
            this.parameters.clear();
            this.closed.set(true);
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        try {
            connection.getExecutor().execute(sql);
            return true;
        } catch (Exception e) {
            throw SQLError.createSQLException(e);
        }
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return (int) sqlResult.getCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void clearBatch() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return closed.get();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return true;
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
