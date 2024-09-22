package com.czcuestc.sqles.engine.query;

import com.czcuestc.sqles.engine.context.SelectContext;

public class SelectBuilder extends Builder {

    private WhereBuilder whereBuilder;

    private SelectItemBuilder selectItemBuilder;

    public SelectBuilder(SelectContext context) {
        super(context);
        init();
    }

    public void init() {
        this.whereBuilder = new WhereBuilder(context);
        this.selectItemBuilder = new SelectItemBuilder(context);
        this.whereBuilder.setSelectItems(selectItemBuilder.getSelectColumnNames());
    }

    public void execute() {
        this.whereBuilder.execute();
        this.selectItemBuilder.execute();
    }
}

