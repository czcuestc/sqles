package com.czcuestc.sqles.engine.domain;

import com.czcuestc.sqles.common.DataType;

import java.io.Serializable;

public class Header implements Serializable {
    private static final long serialVersionUID = -6662376274835273595L;
    public String name;
    public DataType dataType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Header(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }
}
