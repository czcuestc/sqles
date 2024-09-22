package com.czcuestc.sqles.engine.query;

import com.czcuestc.sqles.engine.context.SelectContext;

public abstract class Builder {
    protected SelectContext context;

    public Builder(SelectContext context) {
        this.context = context;
    }

    public abstract void init();

    public abstract void execute();
}
