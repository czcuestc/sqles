package com.czcuestc.sqles.engine.context;

import com.czcuestc.sqles.common.DataType;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Parameters {
    private DataType[] parameterTypes;
    private Object[] parameterValues;

    public Parameters() {
        parameterTypes = new DataType[1 << 3];
        parameterValues = new Object[1 << 3];
    }

    public DataType getType(int index) {
        return parameterTypes[index];
    }

    public Object getParameter(int index) {
        return parameterValues[index];
    }

    public void setType(int index, DataType dataType) {
        resize(index);
        this.parameterTypes[index] = dataType;
    }

    public void setParameter(int index, Object value) {
        resize(index);
        this.parameterValues[index] = value;
    }

    private void resize(int index) {
        if (index >= this.parameterValues.length) {
            Object[] values = new Object[this.parameterValues.length << 1];
            DataType[] types = new DataType[this.parameterValues.length << 1];

            System.arraycopy(this.parameterValues, 0, values, 0, this.parameterValues.length);
            System.arraycopy(this.parameterTypes, 0, types, 0, this.parameterTypes.length);
            this.parameterValues = values;
            this.parameterTypes = types;
        }
    }

    public void clear() {
        for (int i = 0; i < this.parameterValues.length; i++) {
            this.parameterValues[i] = null;
            this.parameterTypes[i] = null;
        }
    }
}
