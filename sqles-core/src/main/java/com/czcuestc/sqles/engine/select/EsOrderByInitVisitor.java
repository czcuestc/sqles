package com.czcuestc.sqles.engine.select;

import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.SelectContext;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.OrderByVisitor;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EsOrderByInitVisitor implements OrderByVisitor {
    private SelectContext context;

    public EsOrderByInitVisitor(SelectContext context) {
        this.context = context;
    }

    @Override
    public void visit(OrderByElement orderBy) {
        Expression expression = orderBy.getExpression();
        if (expression instanceof Column) {
            Column column = (Column) expression;
            if (column != null) {
                FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(column.getColumnName())
                        .order(orderBy.isAsc() ? SortOrder.ASC : SortOrder.DESC);

                context.getOrders().add(fieldSortBuilder);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", orderBy));
    }
}
