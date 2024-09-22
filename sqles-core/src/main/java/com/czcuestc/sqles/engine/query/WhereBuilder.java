package com.czcuestc.sqles.engine.query;


import com.czcuestc.sqles.client.container.HighLevelClients;
import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.CollectionUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import net.sf.jsqlparser.expression.Expression;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhereBuilder extends Builder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhereBuilder.class);

    private Expression where;

    private QueryBuilder queryBuilder;

    private String[] selectItems;

    private SearchSourceBuilder searchSourceBuilder;

    public WhereBuilder(SelectContext context) {
        super(context);
        init();
    }

    @Override
    public void init() {
        this.where = context.getSelect().getWhere();
        if (this.where != null) {
            this.where.accept(context.getEsExpressionVisitorInit());
            ValueContext valueContext = context.pop();
            if (valueContext.isBuilder()) {
                this.queryBuilder = valueContext.getBuilder();
            }
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }

        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.fetchSource(selectItems, null);
        if (!CollectionUtil.isEmpty(context.getOrders())) {
            searchSourceBuilder.sort(context.getOrders());
        }
        if (context.getOffset() != null) {
            searchSourceBuilder.from(context.getOffset());
        }
        if (context.getLimit() != null) {
            searchSourceBuilder.size(context.getLimit());
        }

        context.setSearchSourceBuilder(searchSourceBuilder);
    }

    public String[] getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(String[] selectItems) {
        this.selectItems = selectItems;
    }

    @Override
    public void execute() {
        SearchRequest searchRequest = new SearchRequest(context.getTableName());
        try {
            searchRequest.source(searchSourceBuilder);
            buildHighLight(context.getIndexInfo().getEntityInfo(), searchSourceBuilder);

            RestHighLevelClient client = HighLevelClients.getInstance().getClient(context.getDatasource());
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();

            context.setOutput(hits);
        } catch (Exception e) {
            LOGGER.error("{} execute error", searchSourceBuilder.toString());
            throw new EsException(StringUtil.format("{} execute error", searchSourceBuilder.toString()), e);
        }
    }

    private void buildHighLight(EntityInfo entityInfo, SearchSourceBuilder sourceBuilder) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (FieldInfo fieldInfo : entityInfo.getHighLightFields()) {
            HighlightBuilder.Field field = new HighlightBuilder.Field(fieldInfo.getColumnName());
            field.preTags(fieldInfo.getPreTag());
            field.postTags(fieldInfo.getPostTag());
            field.fragmentSize(fieldInfo.getFragmentSize());
            field.numOfFragments(fieldInfo.getFragmentNumber());

            highlightBuilder.field(field);
        }

        sourceBuilder.highlighter(highlightBuilder);
    }
}
