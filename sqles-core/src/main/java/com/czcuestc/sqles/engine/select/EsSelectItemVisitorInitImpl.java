package com.czcuestc.sqles.engine.select;

import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.engine.context.ValueContext;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;

public class EsSelectItemVisitorInitImpl implements SelectItemVisitor {
    private SelectContext context;

    public EsSelectItemVisitorInitImpl(SelectContext context) {
        this.context = context;
    }

    @Override
    public void visit(SelectItem selectItem) {
        Expression expression = selectItem.getExpression();

        if (expression instanceof Column) {
            Column column = (Column) expression;
            if (column != null) {
                ValueContext itemContext = new ValueContext(column.getColumnName());
                itemContext.setSingleColumn(true);
                context.push(itemContext);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", selectItem));
    }
}