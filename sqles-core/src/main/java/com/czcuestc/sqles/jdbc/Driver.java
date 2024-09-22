package com.czcuestc.sqles.jdbc;

import com.czcuestc.sqles.client.ClientConfig;
import com.czcuestc.sqles.client.ClientUtil;
import com.czcuestc.sqles.client.container.HighLevelClients;
import com.czcuestc.sqles.common.exceptions.SystemException;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.SqlEsExecutor;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Driver implements java.sql.Driver {
    public static final String URL_PREFIX = "jdbc:es://";
    public static final String PLATFORM = getPlatform();
    public static final String OS = getOSName();
    public static final String LICENSE = "AGPL3.0";
    public static final String RUNTIME_VENDOR = System.getProperty("java.vendor");
    public static final String RUNTIME_VERSION = System.getProperty("java.version");
    public static final String VERSION = "1.0.0";
    public static final String NAME = "ES Connector Java";

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getPlatform() {
        return System.getProperty("os.arch");
    }

    public Driver() throws SQLException {
    }

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    public Connection connect(String url, Properties info) throws SQLException {
        if (url == null) {
            throw SQLError.createSQLException("url is empty");
        }

        try {
            ClientConfig clientConfig = ClientUtil.parseURL(url, info);
            HighLevelClients.getInstance().build(clientConfig);
            EsConnection connection = new EsConnection(new SqlEsExecutor(clientConfig.getConnectionString()));
            return connection;
        } catch (Exception ex) {
            throw SQLError.createSQLException(StringUtil.format("{} create connection error", url));
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (url == null) {
            throw SQLError.createSQLException("url is empty");
        }

        if (!StringUtil.startsWithIgnoreCase(url, URL_PREFIX)) {
            throw new SystemException(StringUtil.format("{} format error", url));
        }

        ClientUtil.parseURL(url, null);
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
