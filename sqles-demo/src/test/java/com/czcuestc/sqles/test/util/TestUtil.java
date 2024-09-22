package com.czcuestc.sqles.test.util;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.DataTypeUtil;
import com.czcuestc.sqles.common.util.DateUtil;
import com.czcuestc.sqles.date.DateFormat;
import com.czcuestc.sqles.meta.EntityInfo;
import com.czcuestc.sqles.meta.FieldInfo;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import com.sqles.demo.domain.TestEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class TestUtil {
    public static List<TestEntity> createList(int size, long from) {
        List<TestEntity> list = new ArrayList<>();
        try {
            for (long index = from; index < size + from; index++) {
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
                entity.setKeywordField("es" + index);
                entity.setId(index);
                entity.setDateField(DateUtil.toDate(Constants.DATE.getTime() + index * 3600 * 24 * 1000));
                entity.setNullValue((index % 2) == 0 ? (short) index : null);
                entity.setStringDateField(DateUtil.format(Constants.DATE, DateFormat.STRICT_DATE_TIME_MILLIS.getPattern()));
                entity.setTimestampField(new Timestamp(Constants.DATE.getTime()));
                entity.setLocalDateTimeField(LocalDateTime.now());
                entity.setBoolField((index % 2) == 0 ? true : null);
                entity.setLongDateField(Constants.DATE.getTime());
                entity.setInstantField(Instant.ofEpochMilli(Constants.DATE.getTime()));
                entity.setLocalTimeField(Constants.LOCAL_TIME);
                entity.setTimeField(Constants.TIME);
                entity.setScaledFloatField(Constants.DECIMAL);
                list.add(entity);
            }
        } catch (Exception e) {
        }

        return list;
    }
}
