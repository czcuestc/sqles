package com.czcuestc.sqles.engine.select;

import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.engine.query.SelectBuilder;
import net.sf.jsqlparser.statement.select.*;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EsSelectVisitorInitImpl implements SelectVisitor {
    private SelectContext context;

    public EsSelectVisitorInitImpl(SelectContext context) {
        this.context = context;
    }

    @Override
    public void visit(ParenthesedSelect parenthesedSelect) {

    }

    @Override
    public void visit(PlainSelect plainSelect) {
        EsSelectUtil.init(plainSelect, context);

        SelectBuilder selectBuilder = new SelectBuilder(context);
        selectBuilder.execute();
    }

    @Override
    public void visit(SetOperationList setOpList) {

    }

    @Override
    public void visit(WithItem withItem) {

    }

    @Override
    public void visit(Values aThis) {

    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {

    }

    @Override
    public void visit(TableStatement tableStatement) {

    }
}
