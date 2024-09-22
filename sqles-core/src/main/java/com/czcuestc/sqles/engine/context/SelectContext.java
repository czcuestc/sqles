package com.czcuestc.sqles.engine.context;

import com.czcuestc.sqles.common.util.CollectionUtil;
import com.czcuestc.sqles.engine.domain.Result;
import com.czcuestc.sqles.engine.select.EsExpressionVisitorInitImpl;
import com.czcuestc.sqles.meta.IndexInfo;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.ArrayList;
import java.util.List;

public class SelectContext extends Context {
    private String tableName;

    private String aliasTableName;

    private IndexInfo indexInfo;

    private PlainSelect select;

    private Parameters parameters;
    private SearchHits output;

    private Result result;

    private Integer limit;

    private Integer offset;

    private List<SortBuilder<?>> orders;

    private SearchSourceBuilder searchSourceBuilder;

    public List<SortBuilder<?>> getOrders() {
        if (orders == null) this.orders = new ArrayList<>();

        return this.orders;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAliasTableName() {
        return aliasTableName;
    }

    public void setAliasTableName(String aliasTableName) {
        this.aliasTableName = aliasTableName;
    }

    public IndexInfo getIndexInfo() {
        return indexInfo;
    }

    public void setIndexInfo(IndexInfo indexInfo) {
        this.indexInfo = indexInfo;
    }

    public PlainSelect getSelect() {
        return select;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public void setSelect(PlainSelect select) {
        this.select = select;
    }

    public boolean hasWhere() {
        return this.getSelect().getWhere() != null;
    }

    public boolean hasOrderBy() {
        return !CollectionUtil.isEmpty(this.getSelect().getOrderByElements());
    }

    public boolean isFromTable() {
        FromItem fromItem = this.getSelect().getFromItem();
        if (fromItem instanceof Table) {
            return true;
        }

        return false;
    }

    public boolean hasLimit() {
        return this.getSelect().getLimit() != null;
    }

    public boolean hasPage() {
        return this.hasLimit() || this.hasOffset();
    }

    public boolean hasOffset() {
        return this.getSelect().getOffset() != null;
    }

    public SearchHits getOutput() {
        return output;
    }

    public void setOutput(SearchHits output) {
        this.output = output;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public SearchSourceBuilder getSearchSourceBuilder() {
        return searchSourceBuilder;
    }

    public void setSearchSourceBuilder(SearchSourceBuilder searchSourceBuilder) {
        this.searchSourceBuilder = searchSourceBuilder;
    }

    private EsExpressionVisitorInitImpl esExpressionVisitorInit;
    public EsExpressionVisitorInitImpl getEsExpressionVisitorInit() {
        if (esExpressionVisitorInit == null) esExpressionVisitorInit = new EsExpressionVisitorInitImpl(this);
        return esExpressionVisitorInit;
    }
}
