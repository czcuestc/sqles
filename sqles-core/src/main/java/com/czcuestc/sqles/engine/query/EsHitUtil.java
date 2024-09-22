package com.czcuestc.sqles.engine.query;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.domain.ResultRow;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EsHitUtil {
    public static void processScore(SearchHit searchHit, EntityInfo entityInfo, Map<String, Object> map) {
        FieldInfo fieldInfo = getScoreFieldInfo(entityInfo);
        if (fieldInfo != null) {
            map.put(fieldInfo.getColumnName(), searchHit.getScore());
        }
    }

    public static FieldInfo getScoreFieldInfo(EntityInfo entityInfo) {
        if (entityInfo.getScore() != null) return entityInfo.getScore();

        return entityInfo.getFieldByName("score");
    }

    public static void processHighLight(Map<String, HighlightField> highlightFields, Map<String, Object> map) {
        if (highlightFields == null || highlightFields.size() == 0) return;

        for (String key : highlightFields.keySet()) {
            HighlightField highlightField = highlightFields.get(key);
            map.put(buildMappingKey(key), buildString(highlightField.getFragments()));
        }
    }

    public static String buildMappingKey(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("highlight_").append(key);
        return stringBuilder.toString();
    }

    public static String buildString(Text[] fragments) {
        if (fragments == null || fragments.length == 0) return null;

        StringBuilder builder = new StringBuilder();
        for (Text text : fragments) {
            builder.append(text.string()).append(" ");
        }

        return builder.toString();
    }

    public static void fillRowData(ResultRow row, String[] selectColumnNames, Map<String, Object> sourceAsMap) {
        for (int index = 0; index < selectColumnNames.length; index++) {
            String columnName = selectColumnNames[index];
            String camelName = StringUtil.underlineToCamel(columnName);
            Object value = sourceAsMap.get(columnName);
            if (value == null) {
                value = sourceAsMap.get(camelName);
            }

            row.setValue(columnName, value);
        }
    }

    public Object number(Object value, DataType dataType) {
        if (DataType.isNumberExcludeDecimal(dataType)) {
            Number number = (Number) value;
            switch (dataType) {
                case BYTE:
                    return number.byteValue();
                case INT16:
                    return number.shortValue();
                case INT32:
                    return number.intValue();
                case LONG_TIME:
                case LONG_DATE:
                case LONG_TIMESTAMP:
                case INT64:
                    return number.longValue();
                case FLOAT:
                    return number.floatValue();
                case DOUBLE:
                    return number.doubleValue();
            }
        }

        if (dataType == DataType.DECIMAL) {
            return new BigDecimal(String.valueOf(value));
        }

        return value;
    }
}
