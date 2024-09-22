package com.czcuestc.sqles.jdbc;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.DateUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.domain.Header;
import com.czcuestc.sqles.engine.domain.Result;
import com.czcuestc.sqles.engine.domain.ResultRow;

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
import java.util.List;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ResultSetImpl implements ResultSet {
    private Result result;
    private int rowIndex = -1;
    private boolean wasNullFlag = false;
    private List<Header> headers;
    private ResultRow row;
    private Statement statement;

    public ResultSetImpl(Result result) {
        this.result = result;
        if (result != null) {
            this.headers = result.getHeaders();
        }
    }

    public ResultSetImpl(Result result, Statement statement) {
        this.result = result;
        this.statement = statement;
        if (result != null) {
            this.headers = result.getHeaders();
        }
    }

    private int getOffset() {
        return -1;
    }

    private int getIndex(int index) {
        return index + getOffset();
    }

    private int getIndex(String columnName) {
        return this.result.getColumnIndex(columnName);
    }

    private boolean checkValueNull(Object v) {
        if (v == null) {
            this.wasNullFlag = true;
            return true;
        } else {
            this.wasNullFlag = false;
            return false;
        }
    }

    private static boolean isNumber(DataType type) {
        if (type.getIndex() >= DataType.BYTE.getIndex() && type.getIndex() <= DataType.INT64.getIndex())
            return true;

        return false;
    }

    private static boolean isFloat(DataType type) {
        if (type.getIndex() >= DataType.FLOAT.getIndex() && type.getIndex() <= DataType.DOUBLE.getIndex())
            return true;

        return false;
    }

    private void checkColumnBounds(int columnIndex) throws SQLException {
        if (columnIndex < 1 || columnIndex > this.headers.size()) {
            throw SQLError.createSQLException(StringUtil.concat("columnIndex out of bound", columnIndex));
        }
    }

    @Override
    public boolean next() throws SQLException {
        rowIndex++;
        while (rowIndex < result.size()) {
            this.row = this.result.getResultRow(rowIndex);
            return true;
        }
        return false;
    }

    @Override
    public void close() throws SQLException {
        this.rowIndex = 1;
        this.wasNullFlag = false;
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.wasNullFlag;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getStringInner(index);
    }

    private String getStringInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        if (header.dataType == DataType.STRING) {
            return (String) v;
        }

        return String.valueOf(v);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getBooleanInner(index);
    }

    private boolean getBooleanInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return false;
        }

        if (header.dataType == DataType.BOOL) {
            return (boolean) v;
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "boolean convert error"));
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getByteInner(index);
    }

    private byte getByteInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return 0;
        }

//        if (header.dataType == DataType.BYTE) {
//            return (byte) v;
//        }

        if (isNumber(header.dataType)) {
            Number number = (Number) v;
            return number.byteValue();
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "byte convert error"));
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getShortInner(index);
    }

    private short getShortInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return 0;
        }

//        if (header.dataType == DataType.INT16) {
//            return (short) v;
//        }

        if (isNumber(header.dataType)) {
            Number number = (Number) v;
            return number.shortValue();
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "short convert error"));
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getIntInner(index);
    }

    private int getIntInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return 0;
        }

//        if (header.dataType == DataType.INT32) {
//            return (int) v;
//        }

        if (isNumber(header.dataType)) {
            Number number = (Number) v;
            return number.intValue();
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "int convert error"));
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getLongInner(index);
    }

    private long getLongInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return 0;
        }

//        if (header.dataType == DataType.INT64) {
//            return (long) v;
//        }

        if (isNumber(header.dataType)) {
            Number number = (Number) v;
            return number.longValue();
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "long convert error"));
    }


    @Override
    public float getFloat(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getIntInner(index);
    }

    private float getFloatInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return 0;
        }

//        if (header.dataType == DataType.FLOAT) {
//            return (float) v;
//        }

        if (isFloat(header.dataType)) {
            Number number = (Number) v;
            return number.floatValue();
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "float convert error"));
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getDoubleInner(index);
    }

    private double getDoubleInner(int columnIndex) throws SQLException {
        Header header = this.headers.get(columnIndex);

        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return 0;
        }

//        if (header.dataType == DataType.DOUBLE) {
//            return (double) v;
//        }

        if (isFloat(header.dataType)) {
            Number number = (Number) v;
            return number.doubleValue();
        }

        throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "double convert error"));
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getBigDecimalInner(index, scale);
    }

    private BigDecimal getBigDecimalInner(int columnIndex, int scale) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

//        if (header.dataType == DataType.DECIMAL) {
//            BigDecimal value = (BigDecimal) v;
//            return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
//        }

        try {
            BigDecimal value = new BigDecimal(String.valueOf(v));
            return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "BigDecimal convert error"));
        }
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getDateInner(index);
    }

    private Date getDateInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(rowIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return new Date(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "date convert error"));
        }
    }

    private long toLong(Object value) throws SQLException {
        if (value instanceof Number) {
            Number number = (Number) value;
            return number.longValue();
        }

        throw SQLError.createSQLException(StringUtil.concat(value, "can not convert to long"));
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getTimeInner(index);
    }

    private Time getTimeInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return new Time(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "time convert error"));
        }
    }

    private LocalTime getLocalTimeInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return DateUtil.toLocalTime(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "LocalTime convert error"));
        }
    }

    private LocalDate getLocalDateInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return DateUtil.toLocalDate(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "LocalDate convert error"));
        }
    }

    private LocalDateTime getLocalDateTimeInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return DateUtil.toLocalDateTime(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "LocalDateTime convert error"));
        }
    }

    private Instant getInstantInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return DateUtil.toInstant(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "Instant convert error"));
        }
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getTimestampInner(index);
    }

    public Timestamp getTimestampInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            long epoch = toLong(v);
            return new Timestamp(epoch);
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "timestamp convert error"));
        }
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getStringInner(index);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getBooleanInner(index);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getByteInner(index);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getShortInner(index);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getIntInner(index);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getLongInner(index);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getFloatInner(index);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getDoubleInner(index);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        int index = getIndex(columnLabel);
        return getBigDecimalInner(index, scale);
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getDateInner(index);
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getTimeInner(index);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getTimestampInner(index);
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public String getCursorName() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return new ResultSetMetaImpl(result);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return row.getValue(index);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return row.getValue(index);
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return result.getColumnIndex(columnLabel) + 1;
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    private BigDecimal getBigDecimalInner(int columnIndex) throws SQLException {
        Object v = this.row.getValue(columnIndex);
        if (checkValueNull(v)) {
            return null;
        }

        try {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(v));
            return bigDecimal;
        } catch (Exception e) {
            throw SQLError.createSQLException(StringUtil.concat("column", columnIndex, "BigDecimal convert error"));
        }
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getBigDecimalInner(index);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        int index = getIndex(columnLabel);
        return getBigDecimalInner(index);
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isFirst() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isLast() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void beforeFirst() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void afterLast() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public boolean first() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean last() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getRow() throws SQLException {
        return 0;
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean previous() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
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
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getType() throws SQLException {
        return ResultSet.TYPE_FORWARD_ONLY;
    }

    @Override
    public int getConcurrency() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void insertRow() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateRow() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void deleteRow() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void refreshRow() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public Statement getStatement() throws SQLException {
        return statement;
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public int getHoldability() throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return getString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return getString(columnLabel);
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw SQLError.createSQLFeatureNotSupportedException();

    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        checkColumnBounds(columnIndex);

        int index = getIndex(columnIndex);
        return getObjectInner(index, type);
    }

//    private <T> T getObjectInner(int index, Class<T> type) throws SQLException {
//        Object v;
//        DataType dataType = DataTypeUtil.toDataType(type);
//        switch (dataType) {
//            case BOOL:
//                v = getBooleanInner(index);
//                return (T) v;
//            case BYTE:
//                v = getByteInner(index);
//                return (T) v;
//            case INT16:
//                v = getShortInner(index);
//                return (T) v;
//            case INT32:
//                v = getIntInner(index);
//                return (T) v;
//            case INT64:
//                v = getLongInner(index);
//                return (T) v;
//            case FLOAT:
//                v = getFloatInner(index);
//                return (T) v;
//            case DOUBLE:
//                v = getDoubleInner(index);
//                return (T) v;
//            case DECIMAL:
//                v = getBigDecimalInner(index);
//                return (T) v;
//            case TIME:
//                v = getTimeInner(index);
//                return (T) v;
//            case DATE:
//                v = getDateInner(index);
//                return (T) v;
//            case TIMESTAMP:
//                v = getTimestampInner(index);
//                return (T) v;
//            case STRING:
//                v = getStringInner(index);
//                return (T) v;
//            default:
//                throw SQLError.createSQLFeatureNotSupportedException();
//        }
//    }

    private <T> T getObjectInner(int index, Class<T> type) throws SQLException {
        Object v;
        switch (type.getSimpleName()) {
            case "Boolean":
                v = getBooleanInner(index);
                return (T) v;
            case "Byte":
                v = getByteInner(index);
                return (T) v;
            case "Short":
                v = getShortInner(index);
                return (T) v;
            case "Integer":
                v = getIntInner(index);
                return (T) v;
            case "Long":
                v = getLongInner(index);
                return (T) v;
            case "Float":
                v = getFloatInner(index);
                return (T) v;
            case "Double":
                v = getDoubleInner(index);
                return (T) v;
            case "BigDecimal":
                v = getBigDecimalInner(index);
                return (T) v;
            case "Time":
                v = getTimeInner(index);
                return (T) v;
            case "Date":
                v = getDateInner(index);
                return (T) v;
            case "Timestamp":
                v = getTimestampInner(index);
                return (T) v;
            case "String":
                v = getStringInner(index);
                return (T) v;
            case "LocalTime":
                v = getLocalTimeInner(index);
                return (T) v;
            case "LocalDate":
                v = getLocalDateInner(index);
                return (T) v;
            case "LocalDateTime":
                v = getLocalDateTimeInner(index);
                return (T) v;
            case "Instant":
                v = getInstantInner(index);
                return (T) v;
            default:
                throw SQLError.createSQLFeatureNotSupportedException(type);
        }
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        int index = getIndex(columnLabel);
        return getObjectInner(index, type);
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
