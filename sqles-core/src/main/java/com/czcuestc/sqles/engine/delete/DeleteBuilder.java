package com.czcuestc.sqles.engine.delete;

import com.czcuestc.sqles.engine.context.Context;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import com.czcuestc.sqles.util.IndexUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.delete.Delete;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class DeleteBuilder {
    private EntityInfo entityInfo;

    private ExpressionVisitor expressionVisitor;

    private Context context;

    private long result = 0;

    private QueryBuilder queryBuilder;

    private Delete delete;

    private Expression where;

    public DeleteBuilder(Delete delete, Context context) {
        this.context = context;
        this.delete = delete;
        init();
    }

    private void init() {
        expressionVisitor = new EsDeleteExpressionVisitorInitImpl(context);

        String tableName = delete.getTable().getName();
        IndexInfo indexInfo = IndexInfoManager.getInstance().getIndexInfo(tableName);
        this.entityInfo = indexInfo.getEntityInfo();

        this.where = delete.getWhere();
        if (this.where != null) {
            this.where.accept(expressionVisitor);
            ValueContext valueContext = context.pop();
            if (valueContext.isBuilder()) {
                this.queryBuilder = valueContext.getBuilder();
            }
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
    }

    public void execute() {
        this.result = IndexUtil.deleteByQuery(context.getDatasource(), entityInfo.getIndexName(), queryBuilder);
        context.setCount(this.result);
    }
}
