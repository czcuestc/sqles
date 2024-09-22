package com.czcuestc.sqles.engine.domain;

import com.czcuestc.sqles.engine.context.SqlType;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class SqlResult {
    private SqlType sqlType;
    /**
     * sql操作影响的记录数，如插入10条记录，全部成功则返回10。
     */
    private long count;

    /**
     * 查询返回的结果集
     */
    private Result result;

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
