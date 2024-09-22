package com.czcuestc.sqles.meta;

import com.czcuestc.sqles.annotation.*;
import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EntityInfo {
    protected Class clazz;
    protected String indexName;

    protected FieldInfo id;

    protected FieldInfo routing;

    protected FieldInfo score;

    protected List<FieldInfo> highLightFields = new ArrayList<>();
    protected List<FieldInfo> fieldList = new ArrayList<>();
    protected Map<String, Integer> fieldInfoMap = new HashMap<>();

    public EntityInfo(Class clazz) {
        this.clazz = clazz;
        init(clazz);
    }

    public void init(Class clazz) {
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID"))
                    continue;

                field.setAccessible(true);
                FieldInfo fieldInfo = new FieldInfo(field);
                initField(fieldInfo);

//                if (!fieldInfo.getIndexedField())
//                    continue;

                fieldInfoMap.put(field.getName(), fieldList.size());
                fieldInfoMap.put(fieldInfo.getColumnName(), fieldList.size());
                fieldList.add(fieldInfo);
            }

            clazz = clazz.getSuperclass();
        }

        valid();
    }

    private void valid() {
        if (id == null) {
            throw new EsException(StringUtil.format("{} not set id field", indexName));
        }
    }

    private void initField(FieldInfo fieldInfo) {
        Annotation[] annotations = fieldInfo.getField().getAnnotations();
        if (annotations == null || annotations.length == 0)
            return;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Id) {
                id = fieldInfo;
                continue;
            }

            if (annotation instanceof Routing) {
                routing = fieldInfo;
                continue;
            }

            if (annotation instanceof Score) {
                score = fieldInfo;
                continue;
            }

            if (annotation instanceof BoolField) {
                BoolField field = (BoolField) annotation;
                fieldInfo.setFieldType(FieldType.BOOLEAN);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof ByteField) {
                ByteField field = (ByteField) annotation;
                fieldInfo.setFieldType(FieldType.BYTE);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof ShortField) {
                ShortField field = (ShortField) annotation;
                fieldInfo.setFieldType(FieldType.SHORT);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof IntegerField) {
                IntegerField field = (IntegerField) annotation;
                fieldInfo.setFieldType(FieldType.INTEGER);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof LongField) {
                LongField field = (LongField) annotation;
                fieldInfo.setFieldType(FieldType.LONG);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof FloatField) {
                FloatField field = (FloatField) annotation;
                fieldInfo.setFieldType(FieldType.FLOAT);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof DoubleField) {
                DoubleField field = (DoubleField) annotation;
                fieldInfo.setFieldType(FieldType.DOUBLE);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof HalfFloatField) {
                HalfFloatField field = (HalfFloatField) annotation;
                fieldInfo.setFieldType(FieldType.HALF_FLOAT);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof ScaledFloatField) {
                ScaledFloatField field = (ScaledFloatField) annotation;
                fieldInfo.setFieldType(FieldType.SCALED_FLOAT);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }
                fieldInfo.setScalingFactor(field.scalingFactor());

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof DateField) {
                DateField field = (DateField) annotation;
                fieldInfo.setFieldType(FieldType.DATE);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }
                if (!StringUtil.isEmpty(field.dateFormat())) {
                    fieldInfo.setDateFormat(field.dateFormat());
                }
                fieldInfo.setIndexedField(true);
            }

//            if (annotation instanceof DateNanosField) {
//                DateNanosField field = (DateNanosField) annotation;
//                fieldInfo.setFieldType(FieldType.DATE_NANOS);
//                fieldInfo.setIndex(field.index());
//                fieldInfo.setStore(field.store());
//                fieldInfo.setDocValues(field.docValues());
//                if (!StringUtil.isEmpty(field.nullValue())) {
//                    fieldInfo.setNullValue(field.nullValue());
//                }
//                fieldInfo.setDateFormat(field.dateFormat());
//
//                fieldInfo.setIndexedField(true);
//            }

            if (annotation instanceof IpField) {
                IpField field = (IpField) annotation;
                fieldInfo.setFieldType(FieldType.IP);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                fieldInfo.setNullValue(field.nullValue());

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof RangeField) {
                RangeField field = (RangeField) annotation;
                fieldInfo.setFieldType(field.type());
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof KeyWordField) {
                KeyWordField field = (KeyWordField) annotation;
                fieldInfo.setFieldType(FieldType.KEYWORD);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                fieldInfo.setFieldData(field.fieldData());
                fieldInfo.setNormalizer(field.normalizer());
                fieldInfo.setIndexOptions(field.indexOptions());

                fieldInfo.setIndexedField(true);
            }

            if (annotation instanceof TextField) {
                TextField field = (TextField) annotation;
                fieldInfo.setFieldType(FieldType.TEXT);
                fieldInfo.setIndex(field.index());
                fieldInfo.setStore(field.store());
                fieldInfo.setDocValues(field.docValues());
                if (!StringUtil.isEmpty(field.nullValue())) {
                    fieldInfo.setNullValue(field.nullValue());
                }

                if (!StringUtil.isEmpty(field.analyzer())) {
                    fieldInfo.setAnalyzer(field.analyzer());
                }
                if (!StringUtil.isEmpty(field.searchAnalyzer())) {
                    fieldInfo.setSearchAnalyzer(field.searchAnalyzer());
                }
                if (!StringUtil.isEmpty(field.normalizer())) {
                    fieldInfo.setNormalizer(field.normalizer());
                }
                fieldInfo.setFieldData(field.fieldData());
                fieldInfo.setIndexOptions(field.indexOptions());
                fieldInfo.setIndexedField(true);
            }



//            if (annotation instanceof com.czcuestc.db2cache.annotation.Field) {
//                com.czcuestc.db2cache.annotation.Field field = (com.czcuestc.db2cache.annotation.Field) annotation;
//                if (field.type() == FieldType.AUTO) {
//                    fieldInfo.setFieldType(DataTypeUtil.getFieldType(fieldInfo.getDataType()));
//                } else {
//                    fieldInfo.setFieldType(field.type());
//                }
//                fieldInfo.setIndex(field.index());
//                fieldInfo.setStore(field.store());
//                fieldInfo.setAnalyzer(field.analyzer());
//                fieldInfo.setSearchAnalyzer(field.searchAnalyzer());
//                fieldInfo.setDocValues(field.docValues());
//                fieldInfo.setDateFormat(field.dateFormat());
//                fieldInfo.setIgnoreAbove(field.ignoreAbove());
//                fieldInfo.setScalingFactor(field.scalingFactor());
//                fieldInfo.setIndexOptions(field.indexOptions());
//                fieldInfo.setNullValue(field.nullValue());
//
//                fieldInfo.setIndexedField(true);
//            }

            if (annotation instanceof Highlight) {
                Highlight highlight = (Highlight) annotation;

                fieldInfo.setPreTag(highlight.preTag());
                fieldInfo.setPostTag(highlight.postTag());
                fieldInfo.setFragmentSize(highlight.fragmentSize());
                fieldInfo.setFragmentNumber(highlight.fragmentNumber());

                fieldInfo.setHighLighted(true);
                this.highLightFields.add(fieldInfo);
            }
        }

        if (fieldInfo.isHighLighted() && fieldInfo.getFieldType() != FieldType.TEXT) {
            throw new EsException(StringUtil.format("{} field can not set highlight", fieldInfo.getFieldType().getFieldType()));
        }
    }

    public boolean routed() {
        return this.routing != null;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public FieldInfo getFieldByName(String columnName) {
        Integer index = fieldInfoMap.get(columnName);
        if (index == null) {
            return null;
        }
        FieldInfo field = fieldList.get(index);
        return field;
    }

    public boolean containsKey(String fieldName) {
        return fieldInfoMap.containsKey(fieldName);
    }

    public Integer getFieldIndex(String columnName) {
        return fieldInfoMap.get(columnName);
    }

    public FieldInfo getFieldByIndex(int fieldIndex) {
        return fieldList.get(fieldIndex);
    }

    public Object newObject() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FieldInfo> getFieldList() {
        return fieldList;
    }

    public int sizeOfField() {
        return fieldList.size();
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public FieldInfo getId() {
        return id;
    }

    public void setId(FieldInfo id) {
        this.id = id;
    }

    public FieldInfo getRouting() {
        return routing;
    }

    public void setRouting(FieldInfo routing) {
        this.routing = routing;
    }

    public List<FieldInfo> getHighLightFields() {
        return highLightFields;
    }

    public void setHighLightFields(List<FieldInfo> highLightFields) {
        this.highLightFields = highLightFields;
    }

    public FieldInfo getScore() {
        return score;
    }

    public void setScore(FieldInfo score) {
        this.score = score;
    }
}
