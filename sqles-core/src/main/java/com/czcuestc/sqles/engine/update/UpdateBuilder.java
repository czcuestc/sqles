package com.czcuestc.sqles.engine.update;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.engine.context.Context;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import com.czcuestc.sqles.util.IndexUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;

import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class UpdateBuilder {
    private EntityInfo entityInfo;

    private ExpressionVisitor expressionVisitor;

    private Context context;

    private long result = 0;

    private QueryBuilder queryBuilder;

    private Update update;

    private Expression where;

    private Script script;

    public UpdateBuilder(Update update, Context context) {
        this.context = context;
        this.update = update;
        init();
    }

    private void init() {
        expressionVisitor = new EsUpdateExpressionVisitorInitImpl(context);

        String tableName = update.getTable().getName();
        IndexInfo indexInfo = IndexInfoManager.getInstance().getIndexInfo(tableName);
        this.entityInfo = indexInfo.getEntityInfo();

        this.where = update.getWhere();
        if (this.where != null) {
            this.where.accept(expressionVisitor);
            ValueContext valueContext = context.pop();
            if (valueContext.isBuilder()) {
                this.queryBuilder = valueContext.getBuilder();
            }
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }

        script = buildScript();
    }

    private Script buildScript() {
        StringBuilder stringBuilder = new StringBuilder();
        List<UpdateSet> updateSets = update.getUpdateSets();
        for (int index = 0; index < updateSets.size(); index++) {
            UpdateSet updateSet = updateSets.get(index);
            Column column = updateSet.getColumns().get(0);
            Expression value = updateSet.getValues().get(0);
            value.accept(expressionVisitor);
            ValueContext valueContext = context.pop();
            FieldInfo fieldInfo = entityInfo.getFieldByName(column.getColumnName());
            stringBuilder.append("ctx._source.").append(column.getColumnName());
            if (fieldInfo.getDataType() == DataType.STRING) {
                stringBuilder.append("='").append(valueContext.getValue()).append("';");
            } else if (fieldInfo.getDataType() == DataType.INT64
                    || DataType.isTime(fieldInfo.getDataType())
                    || DataType.isLongTime(fieldInfo.getDataType())) {
                if (valueContext.getValue() != null) {
                    stringBuilder.append("=").append(valueContext.getValue()).append("L;");
                } else {
                    stringBuilder.append("=").append(valueContext.getValue()).append(";");
                }
            } else {
                stringBuilder.append("=").append(valueContext.getValue()).append(";");
            }
        }
        Script script = new Script(stringBuilder.toString());
        return script;
    }

    public void execute() {
        this.result = IndexUtil.updateByQuery(context.getDatasource(), entityInfo.getIndexName(), script, queryBuilder);
        context.setCount(this.result);
    }
}
