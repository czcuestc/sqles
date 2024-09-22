package com.czcuestc.sqles.engine;

import com.czcuestc.sqles.engine.context.Parameters;
import com.czcuestc.sqles.engine.domain.SqlResult;
import com.czcuestc.sqles.engine.query.SQLUtil;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class SqlEsExecutor {
    private String datasource;

    public String getDatasource() {
        return datasource;
    }

    public SqlEsExecutor(String datasource) {
        this.datasource = datasource;
    }

    public SqlResult execute(String sql) {
        return SQLUtil.execute(this.datasource, sql);
    }

    public SqlResult execute(String sql, Parameters parameters) {
        return SQLUtil.execute(this.datasource, sql, parameters);
    }
}
