package com.czcuestc.sqles.engine.domain;

import com.czcuestc.sqles.common.util.ObjectUtil;

import java.io.Serializable;
import java.util.Objects;

public class ResultRowData implements Serializable {
    private static final long serialVersionUID = -8863451849626362871L;
    private Object[] values;

    public ResultRowData() {
    }

    public ResultRowData(Integer columnSize) {
        this.values = new Object[columnSize];
    }

    public void setValue(Integer columnIndex, Object value) {
        this.values[columnIndex] = value;
    }

    public Object getValue(Integer columnIndex) {
        return this.values[columnIndex];
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        ResultRowData resultRowData = (ResultRowData) obj;
        return ObjectUtil.equals(this.values, resultRowData.values);
    }
}
