package com.czcuestc.sqles.engine.select;

import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.exceptions.NotSupportException;
import com.czcuestc.sqles.common.util.DataTypeUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.meta.EntityInfo;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.util.List;

public class EsSelectUtil {
    public static boolean instanceOfColumn(Expression expression) {
        return expression instanceof Column;
    }

    public static void init(PlainSelect select, SelectContext context) {
        if (select.getFromItem() != null) {
            select.getFromItem().accept(new EsFromItemVisitorInitImpl(context));
        }

//        if (select.getSelectItems() != null) {
//            for (SelectItem selectItem : select.getSelectItems()) {
//                selectItem.accept(new EsSelectItemVisitorInitImpl(context));
//            }
//        }

//        if (context.hasWhere()) {
//            select.getWhere().accept(context.getEsExpressionVisitorInit());
//        }

        if (context.hasOrderBy()) {
            List<OrderByElement> orderByElements = select.getOrderByElements();
            EsOrderByInitVisitor esOrderByInitVisitor = new EsOrderByInitVisitor(context);
            for (OrderByElement orderByElement : orderByElements) {
                orderByElement.accept(esOrderByInitVisitor);
            }
        }

        if (context.hasPage()) {
            initLimitOffset(context);
        }
    }

    public static void initLimitOffset(SelectContext context) {
        int offset = 0;
        int limit = -1;
        if (context.hasLimit()) {
            if (context.getSelect().getLimit().getRowCount() != null) {
                context.getSelect().getLimit().getRowCount().accept(context.getEsExpressionVisitorInit());
                ValueContext valueContext = context.pop();
                Number number = DataTypeUtil.toNumber(valueContext.getValue(), valueContext.getDataType());
                limit = number.intValue();
                valid(limit);
            }

            if (context.getSelect().getLimit().getOffset() != null) {
                context.getSelect().getLimit().getOffset().accept(context.getEsExpressionVisitorInit());
                ValueContext valueContext = context.pop();
                Number number = DataTypeUtil.toNumber(valueContext.getValue(), valueContext.getDataType());
                offset = number.intValue();
                valid(offset);
            }
        }

        if (context.hasOffset()) {
            if (context.getSelect().getOffset().getOffset() != null) {
                context.getSelect().getOffset().getOffset().accept(context.getEsExpressionVisitorInit());
                ValueContext valueContext = context.pop();
                Number number = DataTypeUtil.toNumber(valueContext.getValue(), valueContext.getDataType());
                offset = number.intValue();
                valid(offset);
            }
        }

        valid(limit);

        context.setOffset(offset);
        context.setLimit(limit);
    }

    private static void valid(int size) {
        if (size < 0) {
            throw new NotSupportException(StringUtil.format("{} can not be less than 0", size));
        }
    }

    public static void validColumn(String columnName, EntityInfo entityInfo) {
        if (!entityInfo.containsKey(columnName.toLowerCase())) {
            throw new EsException(StringUtil.format("{} column not exist", columnName));
        }
    }
}
