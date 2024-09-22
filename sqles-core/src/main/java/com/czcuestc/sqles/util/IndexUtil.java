package com.czcuestc.sqles.util;

import com.czcuestc.sqles.annotation.IndexOptions;
import com.czcuestc.sqles.client.container.HighLevelClients;
import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.CollectionUtil;
import com.czcuestc.sqles.common.util.DataTypeUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import com.czcuestc.sqles.meta.FieldType;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.EntityInfoManager;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class IndexUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexUtil.class);

    public static boolean createIndex(String datasource, Class<?> clazz) {
        try {
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            CreateIndexRequest request = buildCreateIndexRequest(clazz);
            client.indices().create(request, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            throw new EsException(StringUtil.format("createIndex {} error", clazz), e);
        }
    }

    public static boolean existIndex(String datasource, Class<?> clazz) {
        try {
            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            return existIndex(datasource, entityInfo.getIndexName());
        } catch (Exception e) {
            throw new EsException(StringUtil.format("existIndex {} error", clazz), e);
        }
    }

    public static Map<String, MappingMetadata> getIndexMeta(String datasource, Class<?> clazz) {
        try {
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            GetIndexRequest request = new GetIndexRequest(entityInfo.getIndexName());
            GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
            return getIndexResponse.getMappings();
        } catch (Exception e) {
            throw new EsException(StringUtil.format("existIndex {} error", clazz), e);
        }
    }

    public static boolean existIndex(String datasource, String indexName) {
        try {
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            GetIndexRequest request = new GetIndexRequest(indexName);
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException(StringUtil.format("existIndex {} error", indexName), e);
        }
    }

    public static boolean deleteIndex(String datasource, Class<?> clazz) {
        try {
            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            DeleteIndexRequest request = new DeleteIndexRequest(entityInfo.getIndexName());
            request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse acknowledgedResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (Exception e) {
            throw new EsException(StringUtil.format("deleteIndex {} error", clazz), e);
        }
    }

    public static boolean refreshIndex(String datasource, Class<?> clazz) {
        try {
            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            RefreshRequest request = new RefreshRequest(entityInfo.getIndexName());
            request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            RefreshResponse refreshResponse = client.indices().refresh(request, RequestOptions.DEFAULT);
            if (Objects.equals(refreshResponse.getStatus(), RestStatus.OK)) {
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new EsException(StringUtil.format("refreshIndex {} error", clazz), e);
        }
    }

    public static long count(String datasource, Class<?> clazz) {
        try {
            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            CountRequest countRequest = new CountRequest(entityInfo.getIndexName());
            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
            return countResponse.getCount();
        } catch (Exception e) {
            throw new EsException(StringUtil.format("count {} error", clazz), e);
        }
    }

    public static int insert(String datasource, Class<?> clazz, Object object) {
        try {
            if (object == null) return 0;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            IndexRequest indexRequest = buildInsertRequest(entityInfo, object);
            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            if (Objects.equals(response.status(), RestStatus.CREATED)) {
                return 1;
            } else if (Objects.equals(response.status(), RestStatus.OK)) {
                return 0;
            } else {
                throw new EsException(StringUtil.format("insert {} failed", object));
            }
        } catch (Exception e) {
            throw new EsException(StringUtil.format("insert {} error", clazz), e);
        }
    }

    public static int bulkInsert(String datasource, Class<?> clazz, List objects) {
        try {
            if (CollectionUtil.isEmpty(objects)) return 0;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            BulkRequest bulkRequest = buildBulkInsertRequest(entityInfo, objects);
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            return processBulkInsertResponse(response);
        } catch (Exception e) {
            throw new EsException(StringUtil.format("insert {} error", clazz), e);
        }
    }

//    private static int processResponse(BulkResponse response) {
//        int count = 0;
//        BulkItemResponse[] items = response.getItems();
//        for (int i = 0; i < items.length; i++) {
//            BulkItemResponse item = items[i];
//            if (!item.isFailed()) {
//                count++;
//            } else {
//                LOGGER.error(item.getFailureMessage());
//            }
//        }
//
//        return count;
//    }

    private static BulkRequest buildBulkInsertRequest(EntityInfo entityInfo, List objects) {
        BulkRequest bulkRequest = new BulkRequest(entityInfo.getIndexName());
        for (Object entity : objects) {
            IndexRequest indexRequest = buildInsertRequest(entityInfo, entity);
            bulkRequest.add(indexRequest);
        }
        return bulkRequest;
    }

    private static IndexRequest buildInsertRequest(EntityInfo entityInfo, Object object) {
        try {
            IndexRequest indexRequest = new IndexRequest(entityInfo.getIndexName());
            EntityUtil.setId(indexRequest, entityInfo, object);
            if (entityInfo.routed()) {
                EntityUtil.setRouting(indexRequest, entityInfo, object);
            }
            String json = EntityUtil.toJsonString(object, entityInfo);
            indexRequest.source(json, XContentType.JSON);
            return indexRequest;
        } catch (Exception e) {
            throw new EsException(StringUtil.format("buildInsertRequest error"), e);
        }
    }

    public static Object findById(String datasource, Class<?> clazz, Object id) {
        try {
            if (id == null) return null;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            GetRequest getRequest = new GetRequest(entityInfo.getIndexName(), String.valueOf(id));
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                String sourceAsString = getResponse.getSourceAsString();
                return EntityUtil.toEntity(sourceAsString, getResponse.getId(), entityInfo);
            }

            return null;
        } catch (Exception e) {
            LOGGER.error("findById {} failed", id, e);
            throw new EsException(e);
        }
    }

    public static int deleteById(String datasource, Class<?> clazz, Object id) {
        try {
            if (id == null) return IndexConstants.NUMBER_ZERO;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            DeleteRequest request = new DeleteRequest(entityInfo.getIndexName(), String.valueOf(id));
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            if (Objects.equals(response.status(), RestStatus.OK)) {
                return IndexConstants.NUMBER_ONE;
            }

            return IndexConstants.NUMBER_ZERO;
        } catch (Exception e) {
            LOGGER.error("deleteById {} failed", id, e);
            throw new EsException(e);
        }
    }

    public static int deleteByIds(String datasource, Class<?> clazz, List ids) {
        try {
            if (CollectionUtil.isEmpty(ids)) return IndexConstants.NUMBER_ZERO;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            BulkRequest request = buildDeleteIndexRequest(entityInfo, ids);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
            return processBulkResponse(response);
        } catch (Exception e) {
            LOGGER.error("deleteById {} failed", ids, e);
            throw new EsException(e);
        }
    }

    public static long deleteByQuery(String datasource,String indexName, QueryBuilder queryBuilder) {
        try {
            DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
            request.setQuery(queryBuilder);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            // 执行删除请求
            BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
            return response.getDeleted();
        } catch (Exception e) {
            LOGGER.error("delete {} failed", queryBuilder, e);
            throw new EsException(e);
        }
    }

    public static long updateByQuery(String datasource, String indexName, Script script, QueryBuilder queryBuilder) {
        try {
            UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
            request.setQuery(queryBuilder);
            request.setScript(script);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            BulkByScrollResponse response = client.updateByQuery(request, RequestOptions.DEFAULT);
            return response.getUpdated();
        } catch (Exception e) {
            LOGGER.error("delete {} failed", queryBuilder, e);
            throw new EsException(e);
        }
    }

    private static int processBulkInsertResponse(BulkResponse response) {
        if (!response.hasFailures()) {
            return response.getItems().length;
        }

        int count = 0;
        for (BulkItemResponse bulkItemResponse : response.getItems()) {
            if (Objects.equals(bulkItemResponse.status(), RestStatus.CREATED)) {
                count++;
            } else {
                LOGGER.error("bulk operate failed,{}", bulkItemResponse.getFailureMessage(), bulkItemResponse);
            }
        }

        return count;
    }

    private static int processBulkResponse(BulkResponse response) {
        if (!response.hasFailures()) {
            return response.getItems().length;
        }

        int count = 0;
        for (BulkItemResponse bulkItemResponse : response.getItems()) {
            if (Objects.equals(bulkItemResponse.status(), RestStatus.OK)) {
                count++;
            } else {
                LOGGER.error("bulk operate failed,{}", bulkItemResponse.getFailureMessage(), bulkItemResponse);
            }
        }

        return count;
    }

    private static BulkRequest buildDeleteIndexRequest(EntityInfo entityInfo, List ids) {
        BulkRequest request = new BulkRequest();
        for (int index = 0; index < ids.size(); index++) {
            request.add(new DeleteRequest(entityInfo.getIndexName(), String.valueOf(ids.get(index))));
        }
        return request;
    }

    public static int bulkUpdate(String datasource, Class<?> clazz, List entities) {
        try {
            if (CollectionUtil.isEmpty(entities)) return IndexConstants.NUMBER_ZERO;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            BulkRequest request = buildUpdateIndexRequest(entityInfo, entities);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
            return processBulkResponse(response);
        } catch (Exception e) {
            LOGGER.error("bulkUpdate {} failed", entities, e);
            throw new EsException(e);
        }
    }

    public static int update(String datasource, Class<?> clazz, Object entity) {
        try {
            if (entity == null) return IndexConstants.NUMBER_ZERO;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            UpdateRequest request = buildUpdateIndexRequest(entityInfo, entity);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            if (Objects.equals(response.status(), RestStatus.OK)) {
                return IndexConstants.NUMBER_ONE;
            }

            return IndexConstants.NUMBER_ZERO;
        } catch (Exception e) {
            LOGGER.error("update {} failed", entity, e);
            throw new EsException(e);
        }
    }

    private static BulkRequest buildUpdateIndexRequest(EntityInfo entityInfo, List entities) {
        BulkRequest request = new BulkRequest();
        for (int index = 0; index < entities.size(); index++) {
            UpdateRequest updateRequest = buildUpdateIndexRequest(entityInfo, entities.get(index));
            request.add(updateRequest);
        }
        return request;
    }

    private static UpdateRequest buildUpdateIndexRequest(EntityInfo entityInfo, Object entity) {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(entityInfo.getIndexName());
            updateRequest.id(EntityUtil.getId(entityInfo, entity));
            String json = EntityUtil.toJsonString(entity, entityInfo);
            updateRequest.doc(json, XContentType.JSON);
            return updateRequest;
        } catch (Exception e) {
            throw new EsException(StringUtil.format("buildUpdateIndexRequest error,{}", entity));
        }
    }

    public static boolean exist(String datasource, Class<?> clazz, Object id) {
        try {
            if (id == null) return false;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            GetRequest getRequest = new GetRequest(entityInfo.getIndexName(), String.valueOf(id));
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            return client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            LOGGER.error("exist {} failed", id, e);
            throw new EsException(e);
        }
    }

    public static List findByIds(String datasource, Class<?> clazz, List ids) {
        try {
            if (CollectionUtil.isEmpty(ids)) return null;

            EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
            MultiGetRequest request = buildMultiGetRequest(entityInfo.getIndexName(), ids);
            RestHighLevelClient client = HighLevelClients.getInstance().getClient(datasource);
            MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
            List result = new ArrayList(ids.size());
            for (MultiGetItemResponse itemResponse : response) {
                GetResponse getResponse = itemResponse.getResponse();
                if (getResponse != null && getResponse.isExists()) {
                    String sourceAsString = getResponse.getSourceAsString();
                    Object entity = EntityUtil.toEntity(sourceAsString, getResponse.getId(), entityInfo);
                    result.add(entity);
                }
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("findByIds {} failed", ids, e);
            throw new EsException(e);
        }
    }

    private static MultiGetRequest buildMultiGetRequest(String indexName, List ids) {
        MultiGetRequest request = new MultiGetRequest();
        for (Object id : ids) {
            request.add(new MultiGetRequest.Item(indexName, String.valueOf(id)));
        }
        return request;
    }

    private static CreateIndexRequest buildCreateIndexRequest(Class<?> clazz) {
        EntityInfo entityInfo = EntityInfoManager.getInstance().get(clazz);
        String indexName = entityInfo.getIndexName();
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        IndexInfo indexInfo = IndexInfoManager.getInstance().getIndexInfo(indexName);
        if (!StringUtil.isEmpty(indexInfo.getAliasName())) {
            request.alias(new Alias(indexInfo.getAliasName()));
        }

        if(indexInfo.isSetting()) {
            request.settings(buildSetting(indexInfo));
        }

        initMapping(request, entityInfo);
        return request;
    }

    private static Settings buildSetting(IndexInfo indexInfo) {
        Settings.Builder builder = Settings.builder();
        if (indexInfo.getShards() != null) {
            builder.put(IndexConstants.ES_KEY_NUMBER_OF_SHARDS, indexInfo.getShards());
        }
        if (indexInfo.getReplicas() != null) {
            builder.put(IndexConstants.ES_KEY_NUMBER_OF_REPLICAS, indexInfo.getReplicas());
        }
        if (indexInfo.getMaxResultWindow() != null) {
            builder.put(IndexConstants.ES_KEY_MAX_RESULT_WINDOW, indexInfo.getMaxResultWindow());
        }
        if (!StringUtil.isEmpty(indexInfo.getRefreshInterval())) {
            builder.put(IndexConstants.ES_KEY_REFRESH_INTERVAL, indexInfo.getRefreshInterval());
        }

        return builder.build();
    }

    public static Map<String, Object> toMapping(FieldInfo fieldInfo) {
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> fieldMap = new HashMap<>();
        if (!fieldInfo.getFieldType().isUnknown()) {
            fieldMap.put("type", fieldInfo.getFieldType().getFieldType());
        } else {
            fieldMap.put("type", DataTypeUtil.getFieldType(fieldInfo.getDataType()));
        }
        if (fieldInfo.getIndex() != null) {
            fieldMap.put("index", fieldInfo.getIndex());
        }
        if (fieldInfo.getStore() != null) {
            fieldMap.put("store", fieldInfo.getStore());
        }
        if (fieldInfo.getDocValues() != null) {
            fieldMap.put("doc_values", fieldInfo.getDocValues());
        }
        if (fieldInfo.getNorms() != null) {
            fieldMap.put("norms", fieldInfo.getNorms());
        }
        if (!StringUtil.isEmpty(fieldInfo.getNormalizer())) {
            fieldMap.put("normalizer", fieldInfo.getNormalizer());
        }
        if (!StringUtil.isEmpty(fieldInfo.getAnalyzer())) {
            fieldMap.put("analyzer", fieldInfo.getAnalyzer());
        }
        if (!StringUtil.isEmpty(fieldInfo.getSearchAnalyzer())) {
            fieldMap.put("search_analyzer", fieldInfo.getSearchAnalyzer());
        }
        if (fieldInfo.getScalingFactor() != null) {
            fieldMap.put("scaling_factor", fieldInfo.getScalingFactor());
        }
        if (!StringUtil.isEmpty(fieldInfo.getNullValue())) {
            fieldMap.put("null_value", fieldInfo.getNullValue());
        }
        if (fieldInfo.getIgnoreAbove() != null) {
            fieldMap.put("ignore_above", fieldInfo.getIgnoreAbove());
        }
        if (fieldInfo.getPositionIncrementGap() != null) {
            fieldMap.put("position_increment_gap", fieldInfo.getPositionIncrementGap());
        }
        if (!StringUtil.isEmpty(fieldInfo.getDateFormat())) {
            fieldMap.put("format", fieldInfo.getDateFormat() + "||epoch_millis");
        }
        else {
            if (fieldInfo.getFieldType() == FieldType.DATE) {
                fieldMap.put("format", "epoch_millis");
            }
        }

        if (fieldInfo.getIndexOptions() != null && fieldInfo.getIndexOptions() != IndexOptions.NONE) {
            fieldMap.put("index_options", fieldInfo.getIndexOptions().getOptions());
        }

        map.put(fieldInfo.getColumnName(), fieldMap);
        return map;
    }

    private static void initMapping(CreateIndexRequest request, EntityInfo entityInfo) {
        Map<String, Object> properties = new HashMap<>();
        for (FieldInfo fieldInfo : entityInfo.getFieldList()) {
            if (!fieldInfo.getIndexedField())
                continue;

            Map<String, Object> fieldMap = toMapping(fieldInfo);
            properties.putAll(fieldMap);
        }

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);

        request.mapping(mapping);
    }
}
