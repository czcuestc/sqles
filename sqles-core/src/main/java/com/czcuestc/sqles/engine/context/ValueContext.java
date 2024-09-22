package com.czcuestc.sqles.engine.context;

import com.czcuestc.sqles.common.DataType;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ValueContext {
    private QueryBuilder builder;

    private Object value;

    private Object[] values;

    private DataType dataType;

    private boolean isSingleColumn;

    public boolean isBuilder() {
        return builder != null;
    }

    public boolean isSingleValue() {
        return value != null;
    }

    public boolean isValueArray() {
        return values != null;
    }

    public ValueContext() {
    }

    public ValueContext(Object value) {
        this.value = value;
    }

    public ValueContext(Object value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public ValueContext(QueryBuilder builder) {
        this.builder = builder;
    }

    public ValueContext(Object[] values) {
        this.values = values;
    }

    public QueryBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(QueryBuilder builder) {
        this.builder = builder;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public boolean isSingleColumn() {
        return isSingleColumn;
    }

    public void setSingleColumn(boolean singleColumn) {
        isSingleColumn = singleColumn;
    }

    public Object[] getValues() {
        return values;
    }
}
