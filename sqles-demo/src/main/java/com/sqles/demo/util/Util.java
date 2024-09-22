package com.sqles.demo.util;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.DataTypeUtil;
import com.czcuestc.sqles.common.util.DateUtil;
import com.czcuestc.sqles.date.DateFormat;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import com.sqles.demo.domain.TestEntity;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Util {
    public static Date DATE = new Date();

    public static LocalTime LOCAL_TIME = LocalTime.now();

    public static Time TIME = Time.valueOf(LOCAL_TIME);

    public static BigDecimal DECIMAL = new BigDecimal(99.999);


    public static List<TestEntity> createList(int size, long from) {
        List<TestEntity> list = new ArrayList<>();
        try {
            for (long index = 0; index < size; index++) {
                IndexInfo tableInfo = IndexInfoManager.getInstance().getIndexInfo("test_entity");
                EntityInfo entityInfo = tableInfo.getEntityInfo();
                TestEntity entity = new TestEntity();
                for (FieldInfo fieldInfo : entityInfo.getFieldList()) {
                    if (fieldInfo.getDataType() == DataType.STRING) {
                        fieldInfo.getField().set(entity, fieldInfo.getColumnName() + index);
                    }
                    if (fieldInfo.getDataType() == DataType.BOOL) {
                        fieldInfo.getField().set(entity, index % 2 == 0 ? true : null);
                    }
                    if (fieldInfo.getDataType() == DataType.INT32 || fieldInfo.getDataType() == DataType.INT64
                            || fieldInfo.getDataType() == DataType.INT16 || fieldInfo.getDataType() == DataType.BYTE
                            || fieldInfo.getDataType() == DataType.FLOAT || fieldInfo.getDataType() == DataType.DOUBLE) {
                        Object v = DataTypeUtil.convert(fieldInfo.getDataType(), index);
                        fieldInfo.getField().set(entity, v);
                    }
                }
                entity.setTextField("建索引时默认是根据文档标识符es将文档均分至多个分片。当搜索数据时，默认查询所有分片结果然后汇总，而并不必须知道数据到底存在哪个分片上" + index);
                entity.setId(from + index);
                entity.setDateField(DATE);
                entity.setNullValue((index % 2) == 0 ? (short) index : null);
                entity.setStringDateField(DateUtil.format(DATE, DateFormat.STRICT_DATE_TIME_MILLIS.getPattern()));
                entity.setDateField(DATE);
                entity.setTimestampField(new Timestamp(DATE.getTime()));
                entity.setBoolField((index % 2) == 0 ? true : null);
                entity.setLongDateField(DATE.getTime());
                entity.setLocalDateTimeField(LocalDateTime.now());
                entity.setInstantField(Instant.ofEpochMilli(DATE.getTime()));
                entity.setLocalTimeField(LOCAL_TIME);
                entity.setTimeField(TIME);
                entity.setScaledFloatField(DECIMAL);
                list.add(entity);
            }
        } catch (Exception e) {
        }

        return list;
    }
}
