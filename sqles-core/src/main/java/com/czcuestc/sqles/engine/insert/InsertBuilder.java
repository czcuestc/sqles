package com.czcuestc.sqles.engine.insert;

import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.CollectionUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.Context;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import com.czcuestc.sqles.util.EntityUtil;
import com.czcuestc.sqles.util.IndexUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class InsertBuilder {
    private String[] columnNames;

    private EntityInfo entityInfo;

    private ExpressionVisitor expressionVisitor;

    private Context context;

    private int result = 0;

    private Insert insert;

    private List<Object> entities;

    public InsertBuilder(Insert insert, Context context) {
        this.context = context;
        this.insert = insert;
        init();
    }

    private void init() {
        expressionVisitor = new EsInsertExpressionVisitorInitImpl(context);

        String tableName = insert.getTable().getName();

        IndexInfo indexInfo = IndexInfoManager.getInstance().getIndexInfo(tableName);
        this.entityInfo = indexInfo.getEntityInfo();

        ExpressionList<Column> expressionList = insert.getColumns();
        if (CollectionUtil.isEmpty(expressionList)) {
            throw new EsException(StringUtil.format("{} syntax error", insert));
        }

        columnNames = new String[expressionList.size()];
        for (int index = 0; index < expressionList.size(); index++) {
            columnNames[index] = expressionList.get(index).getColumnName();
        }

        if(insert.getSelect()!=null) {
            Values values = (Values) insert.getSelect();
            ExpressionList<?> expressions = values.getExpressions();
            entities = new ArrayList<>(expressions.size());

            if(expressions instanceof ParenthesedExpressionList) {
                Object entity = parse(expressions);
                entities.add(entity);
            }
            else {
                for (int index = 0; index < expressions.size(); index++) {
                    Expression expression = expressions.get(index);
                    Object entity = parse((ExpressionList<?>) expression);
                    entities.add(entity);
                }
            }
        }
    }

    private Object parse(ExpressionList<?> expressions) {
        expressions.accept(this.expressionVisitor);
        ValueContext valueContext = context.pop();
        Map<String, Object> row = new HashMap<>();
        if (valueContext.isValueArray()) {
            for (int item = 0; item < columnNames.length; item++) {
                row.put(columnNames[item], valueContext.getValues()[item]);
            }
        }
        Object entity = EntityUtil.toEntity(row, entityInfo);
        return entity;
    }

    public void execute() {
        if (CollectionUtil.isEmpty(entities)) return;

        this.result = IndexUtil.bulkInsert(context.getDatasource(), entityInfo.getClazz(), entities);
        context.setCount(this.result);
    }
}
