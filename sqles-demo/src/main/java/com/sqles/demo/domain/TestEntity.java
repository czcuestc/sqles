package com.sqles.demo.domain;

import com.czcuestc.sqles.annotation.*;
import com.czcuestc.sqles.util.Analyzer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
@Document
@Setting(maxResultWindow = 1000)
public class TestEntity implements Serializable {
    private static final long serialVersionUID = -190806307024075427L;
    /**
     * id字段，唯一标识记录，可以根据该字段快速查找记录
     * id字段的值需要设置，若id不设置，操作记录将会失败。
     */
    @Id
    @LongField
    private Long id;

    /**
     * 添加了Highlight注解，默认会将搜索高亮结果内容放入highlight字段，highlight字段变量名
     * 默认为highlight+当前字段名，camel命名
     */
    @Highlight
    @TextField(analyzer = Analyzer.IK_SMART)
    private String textField;

    private String highlightTextField;

    @KeyWordField
    private String keywordField;

    @BoolField
    private Boolean boolField;

    @ByteField
    private Byte byteField;

    @ShortField
    private Short shortField;

    @ShortField
    private Short nullValue;

    @IntegerField
    private Integer integerField;

    /***
     * Routing注解可设置路由字段
     */
    @Routing
    @LongField
    private Long longField;

    @HalfFloatField
    private Float halfFloatField;

    @ScaledFloatField(scalingFactor = 100)
    private BigDecimal scaledFloatField;

    @FloatField
    private Float floatField;

    @DoubleField
    private Double doubleField;

    @DateField
    private String stringDateField;

    @DateField
    private Date dateField;

    @DateField
    private Long longDateField;

    @DateField
    private Time timeField;

    @DateField
    private Timestamp timestampField;

    @DateField
    private LocalTime localTimeField;

    @DateField
    private LocalDateTime localDateTimeField;

    @DateField
    private Instant instantField;

    /**
     * 记录score会默认放入该字段，也可以用Score字段指定。
     */
    private float score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public String getHighlightTextField() {
        return highlightTextField;
    }

    public void setHighlightTextField(String highlightTextField) {
        this.highlightTextField = highlightTextField;
    }

    public String getKeywordField() {
        return keywordField;
    }

    public void setKeywordField(String keywordField) {
        this.keywordField = keywordField;
    }

    public Boolean getBoolField() {
        return boolField;
    }

    public void setBoolField(Boolean boolField) {
        this.boolField = boolField;
    }

    public Byte getByteField() {
        return byteField;
    }

    public void setByteField(Byte byteField) {
        this.byteField = byteField;
    }

    public Short getShortField() {
        return shortField;
    }

    public void setShortField(Short shortField) {
        this.shortField = shortField;
    }

    public Short getNullValue() {
        return nullValue;
    }

    public void setNullValue(Short nullValue) {
        this.nullValue = nullValue;
    }

    public Integer getIntegerField() {
        return integerField;
    }

    public void setIntegerField(Integer integerField) {
        this.integerField = integerField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public Float getHalfFloatField() {
        return halfFloatField;
    }

    public void setHalfFloatField(Float halfFloatField) {
        this.halfFloatField = halfFloatField;
    }

    public BigDecimal getScaledFloatField() {
        return scaledFloatField;
    }

    public void setScaledFloatField(BigDecimal scaledFloatField) {
        this.scaledFloatField = scaledFloatField;
    }

    public Float getFloatField() {
        return floatField;
    }

    public void setFloatField(Float floatField) {
        this.floatField = floatField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Time getTimeField() {
        return timeField;
    }

    public void setTimeField(Time timeField) {
        this.timeField = timeField;
    }

    public Timestamp getTimestampField() {
        return timestampField;
    }

    public void setTimestampField(Timestamp timestampField) {
        this.timestampField = timestampField;
    }

    public LocalTime getLocalTimeField() {
        return localTimeField;
    }

    public void setLocalTimeField(LocalTime localTimeField) {
        this.localTimeField = localTimeField;
    }

    public LocalDateTime getLocalDateTimeField() {
        return localDateTimeField;
    }

    public void setLocalDateTimeField(LocalDateTime localDateTimeField) {
        this.localDateTimeField = localDateTimeField;
    }

    public Instant getInstantField() {
        return instantField;
    }

    public void setInstantField(Instant instantField) {
        this.instantField = instantField;
    }

    public String getStringDateField() {
        return stringDateField;
    }

    public void setStringDateField(String stringDateField) {
        this.stringDateField = stringDateField;
    }

    public Long getLongDateField() {
        return longDateField;
    }

    public void setLongDateField(Long longDateField) {
        this.longDateField = longDateField;
    }
}
