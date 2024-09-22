package com.czcuestc.sqles.util;

import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.DataTypeUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.date.DateModule;
import com.czcuestc.sqles.engine.query.EsHitUtil;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EntityUtil {
    public final static ObjectMapper JSON = new ObjectMapper();

    static {
        JSON.registerModule(new DateModule());
        JSON.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JSON.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
    }

    public static String getId(EntityInfo entityInfo, Object object) {
        try {
            Object id = entityInfo.getId().getField().get(object);
            if (id == null) {
                throw new EsException(StringUtil.format("{} not set id", object));
            }
            return String.valueOf(id);
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not get id", object));
        }
    }

    public static void setId(EntityInfo entityInfo, Object object, String id) {
        try {
            Object value = DataTypeUtil.convert(entityInfo.getId().getDataType(), id);
            if (value == null) {
                throw new EsException(StringUtil.format("{} not convert id {}", object, id));
            }
            entityInfo.getId().getField().set(object, value);
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not set id", object));
        }
    }

    public static String getRouting(EntityInfo entityInfo, Object object) {
        try {
            Object value = entityInfo.getRouting().getField().get(object);
            if (value == null) {
                throw new EsException(StringUtil.format("{} not set routing", object));
            }
            return String.valueOf(value);
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not get routing", object));
        }
    }

    public static void setId(IndexRequest request, String id) {
        request.id(id);
    }

    public static void setId(IndexRequest request, EntityInfo entityInfo, Object object) {
        String id = getId(entityInfo, object);
        setId(request, id);
    }

    public static void setRouting(IndexRequest request, String routing) {
        request.routing(routing);
    }

    public static void setRouting(IndexRequest request, EntityInfo entityInfo, Object object) {
        String routing = getRouting(entityInfo, object);
        setRouting(request, routing);
    }

    public static Object toEntity(String json, String id, EntityInfo entityInfo) {
        try {
            Object entity = JSON.readValue(json, entityInfo.getClazz());
            setId(entityInfo, entity, id);
            return entity;
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not convert entity", json));
        }
    }

    public static Object toEntity(Map<String,Object> map, EntityInfo entityInfo) {
        try {
            return JSON.convertValue(map, entityInfo.getClazz());
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not convert entity", map));
        }
    }

    public static Object toEntity(String json, EntityInfo entityInfo) {
        try {
            return JSON.readValue(json, entityInfo.getClazz());
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not convert entity", json));
        }
    }

    public static String toJsonString(Object entity, EntityInfo entityInfo) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (FieldInfo fieldInfo : entityInfo.getFieldList()) {
                if (!fieldInfo.getIndexedField())
                    continue;

                Object value = fieldInfo.getField().get(entity);
                if (value == null) continue;

                map.put(fieldInfo.getColumnName(), value);
            }

            return JSON.writeValueAsString(map);
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not convert json", entity));
        }
    }

    public static void setScore(EntityInfo entityInfo, Object entity, Object score) {
        try {
            FieldInfo fieldInfo = EsHitUtil.getScoreFieldInfo(entityInfo);
            if (fieldInfo != null && !fieldInfo.getIndexedField()) {
                fieldInfo.getField().set(entity, score);
            }
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not set score", entity));
        }
    }

    public static void setHighlight(EntityInfo entityInfo, Object entity, Map<String, HighlightField> highlightFields) {
        try {
            if (highlightFields == null || highlightFields.size() == 0) return;

            for (String key : highlightFields.keySet()) {
                String columnName = EsHitUtil.buildMappingKey(key);
                FieldInfo fieldInfo = entityInfo.getFieldByName(columnName);
                if (fieldInfo == null) continue;

                String value = EsHitUtil.buildString(highlightFields.get(key).getFragments());
                fieldInfo.getField().set(entity, value);
            }
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} can not set highlight", entity));
        }
    }
}
