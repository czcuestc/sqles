package com.czcuestc.sqles.meta;

import com.czcuestc.sqles.annotation.IndexOptions;
import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.DataTypeUtil;
import com.czcuestc.sqles.common.util.StringUtil;

import java.lang.reflect.Field;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class FieldInfo {
    private Field field;
    private String fieldName;
    private String columnName;
    private DataType dataType;
    private FieldType fieldType = FieldType.AUTO;
    private Boolean index;
    /**
     * 支持date
     */
    private String dateFormat;
    private Boolean store;
    /**
     * 是否为text类型启动fielddata，实现排序和聚合分析
     * 针对分词字段，参与排序或聚合时能提高性能，不分词字段统一建议使用doc_value
     */
    private Boolean fieldData;
    private String searchAnalyzer;
    private String analyzer;
    /**
     * 超过指定数值的字符的文本，将会被忽略，不被索引
     */
    private Integer ignoreAbove;
    /**
     * 是否开启doc_value，用户聚合和排序分析
     * 对不分词字段，默认都是开启，分词字段不能使用，对排序和聚合能提升较大性能，节约内存
     */
    private Boolean docValues;
    /**
     * 存储倒排索引的信息
     */
    private IndexOptions indexOptions = IndexOptions.NONE;
    private Boolean norms;
    /**
     * 设置一些缺失字段的初始化，只有文本可以使用，分词字段的null值也会被分词
     */
    private String nullValue;
    /**
     * 影响距离查询或近似查询，可以设置在多值字段的数据上或分词字段上，查询时可以指定slop间隔，默认值时100
     */
    private Integer positionIncrementGap;
    private Double scalingFactor;
    /**
     * 是否开启自动数据类型转换功能，比如：字符串转数字，浮点转整型
     */
    private Boolean coerce;

    private String normalizer;

    private String preTag;

    private String postTag;

    private Integer fragmentSize;

    private Integer fragmentNumber;

    private boolean indexedField = false;

    private boolean highLighted = false;


    public FieldInfo(Field field) {
        this.field = field;
        this.fieldName = field.getName();
        this.dataType = convert(field);
        this.columnName = StringUtil.camelToUnderline(field.getName());
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    private DataType convert(Field field) {
        Class clazz = field.getType();
        return DataTypeUtil.toDataType(clazz);
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean getIndex() {
        return index;
    }

    public void setIndex(Boolean index) {
        this.index = index;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Boolean getStore() {
        return store;
    }

    public void setStore(Boolean store) {
        this.store = store;
    }

    public Boolean getFieldData() {
        return fieldData;
    }

    public void setFieldData(Boolean fieldData) {
        this.fieldData = fieldData;
    }

    public String getSearchAnalyzer() {
        return searchAnalyzer;
    }

    public void setSearchAnalyzer(String searchAnalyzer) {
        this.searchAnalyzer = searchAnalyzer;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public Integer getIgnoreAbove() {
        return ignoreAbove;
    }

    public void setIgnoreAbove(Integer ignoreAbove) {
        this.ignoreAbove = ignoreAbove;
    }

    public Boolean getDocValues() {
        return docValues;
    }

    public void setDocValues(Boolean docValues) {
        this.docValues = docValues;
    }

    public IndexOptions getIndexOptions() {
        return indexOptions;
    }

    public void setIndexOptions(IndexOptions indexOptions) {
        this.indexOptions = indexOptions;
    }

    public Boolean getNorms() {
        return norms;
    }

    public void setNorms(Boolean norms) {
        this.norms = norms;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public Integer getPositionIncrementGap() {
        return positionIncrementGap;
    }

    public void setPositionIncrementGap(Integer positionIncrementGap) {
        this.positionIncrementGap = positionIncrementGap;
    }

    public Double getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(Double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public Boolean getCoerce() {
        return coerce;
    }

    public void setCoerce(Boolean coerce) {
        this.coerce = coerce;
    }

    public String getNormalizer() {
        return normalizer;
    }

    public void setNormalizer(String normalizer) {
        this.normalizer = normalizer;
    }

    public String getPreTag() {
        return preTag;
    }

    public void setPreTag(String preTag) {
        this.preTag = preTag;
    }

    public String getPostTag() {
        return postTag;
    }

    public void setPostTag(String postTag) {
        this.postTag = postTag;
    }

    public Integer getFragmentSize() {
        return fragmentSize;
    }

    public void setFragmentSize(Integer fragmentSize) {
        this.fragmentSize = fragmentSize;
    }

    public Integer getFragmentNumber() {
        return fragmentNumber;
    }

    public void setFragmentNumber(Integer fragmentNumber) {
        this.fragmentNumber = fragmentNumber;
    }

    public boolean isHighLighted() {
        return highLighted;
    }

    public void setHighLighted(boolean highLighted) {
        this.highLighted = highLighted;
    }

    public Boolean getIndexedField() {
        return indexedField;
    }

    public void setIndexedField(Boolean indexedField) {
        this.indexedField = indexedField;
    }
}
