package com.czcuestc.sqles.engine.query;

import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.engine.domain.Result;
import com.czcuestc.sqles.engine.domain.ResultRow;
import com.czcuestc.sqles.engine.select.EsSelectItemVisitorInitImpl;
import com.czcuestc.sqles.engine.select.EsSelectUtil;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectItemBuilder extends Builder {
    private IndexInfo indexInfo;
    private EntityInfo entityInfo;
    private List<SelectItem<?>> selectItems;
    private String[] selectColumnNames;
    private Result result;
    private int selectColumnSize;
    private EsSelectItemVisitorInitImpl esSelectItemVisitorInit;
    public String[] getSelectColumnNames() {
        return selectColumnNames;
    }

    public SelectItemBuilder(SelectContext context) {
        super(context);
        this.indexInfo = context.getIndexInfo();
        this.entityInfo = indexInfo.getEntityInfo();
        this.esSelectItemVisitorInit = new EsSelectItemVisitorInitImpl(context);
        initSelectItems();
        init();
    }

    private void initSelectItems() {
        boolean hasAll = false;
        for (SelectItem selectItem : this.context.getSelect().getSelectItems()) {
            if (selectItem.getExpression() instanceof AllColumns) {
                this.selectColumnSize += this.entityInfo.sizeOfField();
                hasAll = true;
            } else if (selectItem.getExpression() instanceof AllTableColumns) {
                this.selectColumnSize += this.entityInfo.sizeOfField();
                hasAll = true;
            } else {
                this.selectColumnSize++;
            }
        }

        if (hasAll) {
            selectItems = new ArrayList<>(selectColumnSize);
            for (SelectItem selectItem : context.getSelect().getSelectItems()) {
                if (selectItem.getExpression() instanceof AllColumns) {
                    for (int columnIndex = 0; columnIndex < this.entityInfo.sizeOfField(); columnIndex++) {
                        FieldInfo fieldInfo = this.entityInfo.getFieldByIndex(columnIndex);
                        SelectItem selectExpressionItem = new SelectItem();
                        selectExpressionItem.setExpression(new Column(new Table(this.indexInfo.getIndexName()), fieldInfo.getColumnName()));
                        this.selectItems.add(selectExpressionItem);
                    }
                } else if (selectItem.getExpression() instanceof AllTableColumns) {
                    for (int columnIndex = 0; columnIndex < this.entityInfo.sizeOfField(); columnIndex++) {
                        FieldInfo fieldInfo = this.entityInfo.getFieldByIndex(columnIndex);
                        SelectItem selectExpressionItem = new SelectItem();
                        selectExpressionItem.setExpression(new Column(new Table(this.entityInfo.getIndexName()), fieldInfo.getColumnName()));
                        this.selectItems.add(selectExpressionItem);
                    }
                } else {
                    this.selectItems.add(selectItem);
                }
            }
        } else {
            this.selectItems = this.context.getSelect().getSelectItems();
        }

        this.selectColumnNames = new String[selectItems.size()];
    }

    @Override
    public void init() {
        for (int index = 0; index < selectItems.size(); index++) {
            SelectItem selectItem = selectItems.get(index);
            selectItem.accept(this.esSelectItemVisitorInit);
            ValueContext valueContext = context.pop();
            String columnName = (String) valueContext.getValue();
            EsSelectUtil.validColumn(columnName, entityInfo);
            this.selectColumnNames[index] = columnName;
        }

        initResult();
    }

    private void initResult() {
        this.result = new Result();
        context.setResult(this.result);
        for (int index = 0; index < this.selectColumnSize; index++) {
            String columnName = selectColumnNames[index];
            FieldInfo fieldInfo = this.entityInfo.getFieldByName(columnName);
            this.result.newColumn(selectColumnNames[index], fieldInfo.getDataType());
        }
    }

    @Override
    public void execute() {
        try {
            SearchHits searchHits = context.getOutput();
            for (SearchHit searchHit : searchHits) {
                ResultRow row = this.result.newResultRow();
                Map<String, Object> map = searchHit.getSourceAsMap();
                EsHitUtil.processScore(searchHit, entityInfo, map);
                EsHitUtil.processHighLight(searchHit.getHighlightFields(), map);
                EsHitUtil.fillRowData(row, selectColumnNames, map);
            }
        } finally {
            clear();
        }
    }

    private void clear() {
    }
}
