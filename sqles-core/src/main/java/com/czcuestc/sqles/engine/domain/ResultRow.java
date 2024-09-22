package com.czcuestc.sqles.engine.domain;

import java.io.Serializable;
import java.util.Map;

public class ResultRow implements Serializable {
    private static final long serialVersionUID = 6913257936409349650L;

    protected Map<String, Integer> headerIndexes;
    protected ResultRowData row;

    public ResultRow() {
    }

    public ResultRow(Map<String, Integer> headerIndexes, ResultRowData row) {
        this.headerIndexes = headerIndexes;
        this.row = row;
    }

    public void setValue(String column, Object value) {
        int columnIndex = this.headerIndexes.get(column);
        this.row.setValue(columnIndex, value);
    }

    public Object getValue(String column) {
        int columnIndex = this.headerIndexes.get(column);
        return this.row.getValue(columnIndex);
    }

    public void setValue(int columnIndex, Object value) {
        this.row.setValue(columnIndex, value);
    }

    public Object getValue(int columnIndex) {
        return this.row.getValue(columnIndex);
    }
}
