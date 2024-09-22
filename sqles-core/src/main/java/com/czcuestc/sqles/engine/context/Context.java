package com.czcuestc.sqles.engine.context;

import java.util.Stack;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Context extends Stack<ValueContext> {
    private String datasource;

    private Parameters parameters;

    private long count;

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}
