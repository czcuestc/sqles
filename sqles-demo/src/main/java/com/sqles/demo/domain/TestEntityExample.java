package com.sqles.demo.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TestEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestEntityExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTextFieldIsNull() {
            addCriterion("text_field is null");
            return (Criteria) this;
        }

        public Criteria andTextFieldIsNotNull() {
            addCriterion("text_field is not null");
            return (Criteria) this;
        }

        public Criteria andTextFieldEqualTo(String value) {
            addCriterion("text_field =", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldNotEqualTo(String value) {
            addCriterion("text_field <>", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldGreaterThan(String value) {
            addCriterion("text_field >", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldGreaterThanOrEqualTo(String value) {
            addCriterion("text_field >=", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldLessThan(String value) {
            addCriterion("text_field <", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldLessThanOrEqualTo(String value) {
            addCriterion("text_field <=", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldLike(String value) {
            addCriterion("text_field like", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldNotLike(String value) {
            addCriterion("text_field not like", value, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldIn(List<String> values) {
            addCriterion("text_field in", values, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldNotIn(List<String> values) {
            addCriterion("text_field not in", values, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldBetween(String value1, String value2) {
            addCriterion("text_field between", value1, value2, "textField");
            return (Criteria) this;
        }

        public Criteria andTextFieldNotBetween(String value1, String value2) {
            addCriterion("text_field not between", value1, value2, "textField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldIsNull() {
            addCriterion("keyword_field is null");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldIsNotNull() {
            addCriterion("keyword_field is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldEqualTo(String value) {
            addCriterion("keyword_field =", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldNotEqualTo(String value) {
            addCriterion("keyword_field <>", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldGreaterThan(String value) {
            addCriterion("keyword_field >", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldGreaterThanOrEqualTo(String value) {
            addCriterion("keyword_field >=", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldLessThan(String value) {
            addCriterion("keyword_field <", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldLessThanOrEqualTo(String value) {
            addCriterion("keyword_field <=", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldLike(String value) {
            addCriterion("keyword_field like", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldNotLike(String value) {
            addCriterion("keyword_field not like", value, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldIn(List<String> values) {
            addCriterion("keyword_field in", values, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldNotIn(List<String> values) {
            addCriterion("keyword_field not in", values, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldBetween(String value1, String value2) {
            addCriterion("keyword_field between", value1, value2, "keywordField");
            return (Criteria) this;
        }

        public Criteria andKeywordFieldNotBetween(String value1, String value2) {
            addCriterion("keyword_field not between", value1, value2, "keywordField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldIsNull() {
            addCriterion("bool_field is null");
            return (Criteria) this;
        }

        public Criteria andBoolFieldIsNotNull() {
            addCriterion("bool_field is not null");
            return (Criteria) this;
        }

        public Criteria andBoolFieldEqualTo(Boolean value) {
            addCriterion("bool_field =", value, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldNotEqualTo(Boolean value) {
            addCriterion("bool_field <>", value, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldGreaterThan(Boolean value) {
            addCriterion("bool_field >", value, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldGreaterThanOrEqualTo(Boolean value) {
            addCriterion("bool_field >=", value, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldLessThan(Boolean value) {
            addCriterion("bool_field <", value, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldLessThanOrEqualTo(Boolean value) {
            addCriterion("bool_field <=", value, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldIn(List<Boolean> values) {
            addCriterion("bool_field in", values, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldNotIn(List<Boolean> values) {
            addCriterion("bool_field not in", values, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldBetween(Boolean value1, Boolean value2) {
            addCriterion("bool_field between", value1, value2, "boolField");
            return (Criteria) this;
        }

        public Criteria andBoolFieldNotBetween(Boolean value1, Boolean value2) {
            addCriterion("bool_field not between", value1, value2, "boolField");
            return (Criteria) this;
        }

        public Criteria andByteFieldIsNull() {
            addCriterion("byte_field is null");
            return (Criteria) this;
        }

        public Criteria andByteFieldIsNotNull() {
            addCriterion("byte_field is not null");
            return (Criteria) this;
        }

        public Criteria andByteFieldEqualTo(Byte value) {
            addCriterion("byte_field =", value, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldNotEqualTo(Byte value) {
            addCriterion("byte_field <>", value, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldGreaterThan(Byte value) {
            addCriterion("byte_field >", value, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldGreaterThanOrEqualTo(Byte value) {
            addCriterion("byte_field >=", value, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldLessThan(Byte value) {
            addCriterion("byte_field <", value, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldLessThanOrEqualTo(Byte value) {
            addCriterion("byte_field <=", value, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldIn(List<Byte> values) {
            addCriterion("byte_field in", values, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldNotIn(List<Byte> values) {
            addCriterion("byte_field not in", values, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldBetween(Byte value1, Byte value2) {
            addCriterion("byte_field between", value1, value2, "byteField");
            return (Criteria) this;
        }

        public Criteria andByteFieldNotBetween(Byte value1, Byte value2) {
            addCriterion("byte_field not between", value1, value2, "byteField");
            return (Criteria) this;
        }

        public Criteria andShortFieldIsNull() {
            addCriterion("short_field is null");
            return (Criteria) this;
        }

        public Criteria andShortFieldIsNotNull() {
            addCriterion("short_field is not null");
            return (Criteria) this;
        }

        public Criteria andShortFieldEqualTo(Short value) {
            addCriterion("short_field =", value, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldNotEqualTo(Short value) {
            addCriterion("short_field <>", value, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldGreaterThan(Short value) {
            addCriterion("short_field >", value, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldGreaterThanOrEqualTo(Short value) {
            addCriterion("short_field >=", value, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldLessThan(Short value) {
            addCriterion("short_field <", value, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldLessThanOrEqualTo(Short value) {
            addCriterion("short_field <=", value, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldIn(List<Short> values) {
            addCriterion("short_field in", values, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldNotIn(List<Short> values) {
            addCriterion("short_field not in", values, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldBetween(Short value1, Short value2) {
            addCriterion("short_field between", value1, value2, "shortField");
            return (Criteria) this;
        }

        public Criteria andShortFieldNotBetween(Short value1, Short value2) {
            addCriterion("short_field not between", value1, value2, "shortField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldIsNull() {
            addCriterion("integer_field is null");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldIsNotNull() {
            addCriterion("integer_field is not null");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldEqualTo(Integer value) {
            addCriterion("integer_field =", value, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldNotEqualTo(Integer value) {
            addCriterion("integer_field <>", value, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldGreaterThan(Integer value) {
            addCriterion("integer_field >", value, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldGreaterThanOrEqualTo(Integer value) {
            addCriterion("integer_field >=", value, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldLessThan(Integer value) {
            addCriterion("integer_field <", value, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldLessThanOrEqualTo(Integer value) {
            addCriterion("integer_field <=", value, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldIn(List<Integer> values) {
            addCriterion("integer_field in", values, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldNotIn(List<Integer> values) {
            addCriterion("integer_field not in", values, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldBetween(Integer value1, Integer value2) {
            addCriterion("integer_field between", value1, value2, "integerField");
            return (Criteria) this;
        }

        public Criteria andIntegerFieldNotBetween(Integer value1, Integer value2) {
            addCriterion("integer_field not between", value1, value2, "integerField");
            return (Criteria) this;
        }

        public Criteria andLongFieldIsNull() {
            addCriterion("long_field is null");
            return (Criteria) this;
        }

        public Criteria andLongFieldIsNotNull() {
            addCriterion("long_field is not null");
            return (Criteria) this;
        }

        public Criteria andLongFieldEqualTo(Long value) {
            addCriterion("long_field =", value, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldNotEqualTo(Long value) {
            addCriterion("long_field <>", value, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldGreaterThan(Long value) {
            addCriterion("long_field >", value, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldGreaterThanOrEqualTo(Long value) {
            addCriterion("long_field >=", value, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldLessThan(Long value) {
            addCriterion("long_field <", value, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldLessThanOrEqualTo(Long value) {
            addCriterion("long_field <=", value, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldIn(List<Long> values) {
            addCriterion("long_field in", values, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldNotIn(List<Long> values) {
            addCriterion("long_field not in", values, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldBetween(Long value1, Long value2) {
            addCriterion("long_field between", value1, value2, "longField");
            return (Criteria) this;
        }

        public Criteria andLongFieldNotBetween(Long value1, Long value2) {
            addCriterion("long_field not between", value1, value2, "longField");
            return (Criteria) this;
        }

        public Criteria andNullValueIsNull() {
            addCriterion("null_value is null");
            return (Criteria) this;
        }

        public Criteria andNullValueIsNotNull() {
            addCriterion("null_value is not null");
            return (Criteria) this;
        }

        public Criteria andNullValueEqualTo(Short value) {
            addCriterion("null_value =", value, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueNotEqualTo(Short value) {
            addCriterion("null_value <>", value, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueGreaterThan(Short value) {
            addCriterion("null_value >", value, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueGreaterThanOrEqualTo(Short value) {
            addCriterion("null_value >=", value, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueLessThan(Short value) {
            addCriterion("null_value <", value, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueLessThanOrEqualTo(Short value) {
            addCriterion("null_value <=", value, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueIn(List<Short> values) {
            addCriterion("null_value in", values, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueNotIn(List<Short> values) {
            addCriterion("null_value not in", values, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueBetween(Short value1, Short value2) {
            addCriterion("null_value between", value1, value2, "nullValue");
            return (Criteria) this;
        }

        public Criteria andNullValueNotBetween(Short value1, Short value2) {
            addCriterion("null_value not between", value1, value2, "nullValue");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldIsNull() {
            addCriterion("half_float_field is null");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldIsNotNull() {
            addCriterion("half_float_field is not null");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldEqualTo(Float value) {
            addCriterion("half_float_field =", value, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldNotEqualTo(Float value) {
            addCriterion("half_float_field <>", value, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldGreaterThan(Float value) {
            addCriterion("half_float_field >", value, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldGreaterThanOrEqualTo(Float value) {
            addCriterion("half_float_field >=", value, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldLessThan(Float value) {
            addCriterion("half_float_field <", value, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldLessThanOrEqualTo(Float value) {
            addCriterion("half_float_field <=", value, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldIn(List<Float> values) {
            addCriterion("half_float_field in", values, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldNotIn(List<Float> values) {
            addCriterion("half_float_field not in", values, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldBetween(Float value1, Float value2) {
            addCriterion("half_float_field between", value1, value2, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andHalfFloatFieldNotBetween(Float value1, Float value2) {
            addCriterion("half_float_field not between", value1, value2, "halfFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldIsNull() {
            addCriterion("scaled_float_field is null");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldIsNotNull() {
            addCriterion("scaled_float_field is not null");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldEqualTo(BigDecimal value) {
            addCriterion("scaled_float_field =", value, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldNotEqualTo(BigDecimal value) {
            addCriterion("scaled_float_field <>", value, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldGreaterThan(BigDecimal value) {
            addCriterion("scaled_float_field >", value, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("scaled_float_field >=", value, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldLessThan(BigDecimal value) {
            addCriterion("scaled_float_field <", value, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldLessThanOrEqualTo(BigDecimal value) {
            addCriterion("scaled_float_field <=", value, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldIn(List<BigDecimal> values) {
            addCriterion("scaled_float_field in", values, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldNotIn(List<BigDecimal> values) {
            addCriterion("scaled_float_field not in", values, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("scaled_float_field between", value1, value2, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andScaledFloatFieldNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("scaled_float_field not between", value1, value2, "scaledFloatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldIsNull() {
            addCriterion("float_field is null");
            return (Criteria) this;
        }

        public Criteria andFloatFieldIsNotNull() {
            addCriterion("float_field is not null");
            return (Criteria) this;
        }

        public Criteria andFloatFieldEqualTo(Float value) {
            addCriterion("float_field =", value, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldNotEqualTo(Float value) {
            addCriterion("float_field <>", value, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldGreaterThan(Float value) {
            addCriterion("float_field >", value, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldGreaterThanOrEqualTo(Float value) {
            addCriterion("float_field >=", value, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldLessThan(Float value) {
            addCriterion("float_field <", value, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldLessThanOrEqualTo(Float value) {
            addCriterion("float_field <=", value, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldIn(List<Float> values) {
            addCriterion("float_field in", values, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldNotIn(List<Float> values) {
            addCriterion("float_field not in", values, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldBetween(Float value1, Float value2) {
            addCriterion("float_field between", value1, value2, "floatField");
            return (Criteria) this;
        }

        public Criteria andFloatFieldNotBetween(Float value1, Float value2) {
            addCriterion("float_field not between", value1, value2, "floatField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldIsNull() {
            addCriterion("double_field is null");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldIsNotNull() {
            addCriterion("double_field is not null");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldEqualTo(Double value) {
            addCriterion("double_field =", value, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldNotEqualTo(Double value) {
            addCriterion("double_field <>", value, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldGreaterThan(Double value) {
            addCriterion("double_field >", value, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldGreaterThanOrEqualTo(Double value) {
            addCriterion("double_field >=", value, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldLessThan(Double value) {
            addCriterion("double_field <", value, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldLessThanOrEqualTo(Double value) {
            addCriterion("double_field <=", value, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldIn(List<Double> values) {
            addCriterion("double_field in", values, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldNotIn(List<Double> values) {
            addCriterion("double_field not in", values, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldBetween(Double value1, Double value2) {
            addCriterion("double_field between", value1, value2, "doubleField");
            return (Criteria) this;
        }

        public Criteria andDoubleFieldNotBetween(Double value1, Double value2) {
            addCriterion("double_field not between", value1, value2, "doubleField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldIsNull() {
            addCriterion("String_date_field is null");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldIsNotNull() {
            addCriterion("String_date_field is not null");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldEqualTo(String value) {
            addCriterion("String_date_field =", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldNotEqualTo(String value) {
            addCriterion("String_date_field <>", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldGreaterThan(String value) {
            addCriterion("String_date_field >", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldGreaterThanOrEqualTo(String value) {
            addCriterion("String_date_field >=", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldLessThan(String value) {
            addCriterion("String_date_field <", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldLessThanOrEqualTo(String value) {
            addCriterion("String_date_field <=", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldLike(String value) {
            addCriterion("String_date_field like", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldNotLike(String value) {
            addCriterion("String_date_field not like", value, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldIn(List<String> values) {
            addCriterion("String_date_field in", values, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldNotIn(List<String> values) {
            addCriterion("String_date_field not in", values, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldBetween(String value1, String value2) {
            addCriterion("String_date_field between", value1, value2, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andStringDateFieldNotBetween(String value1, String value2) {
            addCriterion("String_date_field not between", value1, value2, "stringDateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldIsNull() {
            addCriterion("date_field is null");
            return (Criteria) this;
        }

        public Criteria andDateFieldIsNotNull() {
            addCriterion("date_field is not null");
            return (Criteria) this;
        }

        public Criteria andDateFieldEqualTo(Date value) {
            addCriterion("date_field =", value, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldNotEqualTo(Date value) {
            addCriterion("date_field <>", value, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldGreaterThan(Date value) {
            addCriterion("date_field >", value, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldGreaterThanOrEqualTo(Date value) {
            addCriterion("date_field >=", value, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldLessThan(Date value) {
            addCriterion("date_field <", value, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldLessThanOrEqualTo(Date value) {
            addCriterion("date_field <=", value, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldIn(List<Date> values) {
            addCriterion("date_field in", values, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldNotIn(List<Date> values) {
            addCriterion("date_field not in", values, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldBetween(Date value1, Date value2) {
            addCriterion("date_field between", value1, value2, "dateField");
            return (Criteria) this;
        }

        public Criteria andDateFieldNotBetween(Date value1, Date value2) {
            addCriterion("date_field not between", value1, value2, "dateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldIsNull() {
            addCriterion("long_date_field is null");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldIsNotNull() {
            addCriterion("long_date_field is not null");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldEqualTo(Long value) {
            addCriterion("long_date_field =", value, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldNotEqualTo(Long value) {
            addCriterion("long_date_field <>", value, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldGreaterThan(Long value) {
            addCriterion("long_date_field >", value, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldGreaterThanOrEqualTo(Long value) {
            addCriterion("long_date_field >=", value, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldLessThan(Long value) {
            addCriterion("long_date_field <", value, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldLessThanOrEqualTo(Long value) {
            addCriterion("long_date_field <=", value, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldIn(List<Long> values) {
            addCriterion("long_date_field in", values, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldNotIn(List<Long> values) {
            addCriterion("long_date_field not in", values, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldBetween(Long value1, Long value2) {
            addCriterion("long_date_field between", value1, value2, "longDateField");
            return (Criteria) this;
        }

        public Criteria andLongDateFieldNotBetween(Long value1, Long value2) {
            addCriterion("long_date_field not between", value1, value2, "longDateField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldIsNull() {
            addCriterion("time_field is null");
            return (Criteria) this;
        }

        public Criteria andTimeFieldIsNotNull() {
            addCriterion("time_field is not null");
            return (Criteria) this;
        }

        public Criteria andTimeFieldEqualTo(Date value) {
            addCriterionForJDBCTime("time_field =", value, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldNotEqualTo(Date value) {
            addCriterionForJDBCTime("time_field <>", value, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldGreaterThan(Date value) {
            addCriterionForJDBCTime("time_field >", value, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_field >=", value, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldLessThan(Date value) {
            addCriterionForJDBCTime("time_field <", value, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_field <=", value, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldIn(List<Date> values) {
            addCriterionForJDBCTime("time_field in", values, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldNotIn(List<Date> values) {
            addCriterionForJDBCTime("time_field not in", values, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_field between", value1, value2, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimeFieldNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_field not between", value1, value2, "timeField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldIsNull() {
            addCriterion("timestamp_field is null");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldIsNotNull() {
            addCriterion("timestamp_field is not null");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldEqualTo(Date value) {
            addCriterion("timestamp_field =", value, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldNotEqualTo(Date value) {
            addCriterion("timestamp_field <>", value, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldGreaterThan(Date value) {
            addCriterion("timestamp_field >", value, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldGreaterThanOrEqualTo(Date value) {
            addCriterion("timestamp_field >=", value, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldLessThan(Date value) {
            addCriterion("timestamp_field <", value, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldLessThanOrEqualTo(Date value) {
            addCriterion("timestamp_field <=", value, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldIn(List<Date> values) {
            addCriterion("timestamp_field in", values, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldNotIn(List<Date> values) {
            addCriterion("timestamp_field not in", values, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldBetween(Date value1, Date value2) {
            addCriterion("timestamp_field between", value1, value2, "timestampField");
            return (Criteria) this;
        }

        public Criteria andTimestampFieldNotBetween(Date value1, Date value2) {
            addCriterion("timestamp_field not between", value1, value2, "timestampField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldIsNull() {
            addCriterion("local_time_field is null");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldIsNotNull() {
            addCriterion("local_time_field is not null");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldEqualTo(Date value) {
            addCriterionForJDBCTime("local_time_field =", value, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldNotEqualTo(Date value) {
            addCriterionForJDBCTime("local_time_field <>", value, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldGreaterThan(Date value) {
            addCriterionForJDBCTime("local_time_field >", value, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("local_time_field >=", value, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldLessThan(Date value) {
            addCriterionForJDBCTime("local_time_field <", value, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("local_time_field <=", value, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldIn(List<Date> values) {
            addCriterionForJDBCTime("local_time_field in", values, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldNotIn(List<Date> values) {
            addCriterionForJDBCTime("local_time_field not in", values, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("local_time_field between", value1, value2, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalTimeFieldNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("local_time_field not between", value1, value2, "localTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldIsNull() {
            addCriterion("local_date_time_field is null");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldIsNotNull() {
            addCriterion("local_date_time_field is not null");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldEqualTo(Date value) {
            addCriterion("local_date_time_field =", value, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldNotEqualTo(Date value) {
            addCriterion("local_date_time_field <>", value, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldGreaterThan(Date value) {
            addCriterion("local_date_time_field >", value, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldGreaterThanOrEqualTo(Date value) {
            addCriterion("local_date_time_field >=", value, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldLessThan(Date value) {
            addCriterion("local_date_time_field <", value, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldLessThanOrEqualTo(Date value) {
            addCriterion("local_date_time_field <=", value, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldIn(List<Date> values) {
            addCriterion("local_date_time_field in", values, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldNotIn(List<Date> values) {
            addCriterion("local_date_time_field not in", values, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldBetween(Date value1, Date value2) {
            addCriterion("local_date_time_field between", value1, value2, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andLocalDateTimeFieldNotBetween(Date value1, Date value2) {
            addCriterion("local_date_time_field not between", value1, value2, "localDateTimeField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldIsNull() {
            addCriterion("instant_field is null");
            return (Criteria) this;
        }

        public Criteria andInstantFieldIsNotNull() {
            addCriterion("instant_field is not null");
            return (Criteria) this;
        }

        public Criteria andInstantFieldEqualTo(Date value) {
            addCriterion("instant_field =", value, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldNotEqualTo(Date value) {
            addCriterion("instant_field <>", value, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldGreaterThan(Date value) {
            addCriterion("instant_field >", value, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldGreaterThanOrEqualTo(Date value) {
            addCriterion("instant_field >=", value, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldLessThan(Date value) {
            addCriterion("instant_field <", value, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldLessThanOrEqualTo(Date value) {
            addCriterion("instant_field <=", value, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldIn(List<Date> values) {
            addCriterion("instant_field in", values, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldNotIn(List<Date> values) {
            addCriterion("instant_field not in", values, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldBetween(Date value1, Date value2) {
            addCriterion("instant_field between", value1, value2, "instantField");
            return (Criteria) this;
        }

        public Criteria andInstantFieldNotBetween(Date value1, Date value2) {
            addCriterion("instant_field not between", value1, value2, "instantField");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}