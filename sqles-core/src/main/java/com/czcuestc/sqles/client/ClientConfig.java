package com.czcuestc.sqles.client;

import com.czcuestc.sqles.common.util.StringUtil;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ClientConfig {
    private String dataSource = "";
    private int connectTimeout = 30000;
    private int socketTimeout = 30000;
    private int requestTimeout = 30000;
    private int maxConnections = 1000;
    private int maxConnPerRoute = 1000;
    private String userName;
    private String password;
    private String connectionString;
    private String schema = "http";
    private String catalog;
    private int keepAliveTime = 30000;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        if (StringUtil.isEmpty(dataSource)) return;
        this.dataSource = dataSource;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (StringUtil.isEmpty(userName)) return;

        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (StringUtil.isEmpty(password)) return;

        this.password = password;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        if (StringUtil.isEmpty(connectionString)) return;
        this.connectionString = connectionString;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        if (StringUtil.isEmpty(schema)) return;
        this.schema = schema;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        if (StringUtil.isEmpty(catalog)) return;
        this.catalog = catalog;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Integer keepAliveTime) {
        if (keepAliveTime == null) return;
        this.keepAliveTime = keepAliveTime;
    }
}
