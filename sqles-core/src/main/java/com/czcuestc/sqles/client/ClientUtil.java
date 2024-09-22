package com.czcuestc.sqles.client;

import com.czcuestc.sqles.client.container.HighLevelClients;
import com.czcuestc.sqles.common.exceptions.SystemException;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.jdbc.Driver;

import java.util.Map;
import java.util.Properties;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ClientUtil {
    public static void init(String datasource, Map configs) {
        if (configs == null || configs.size() == 0) return;

        if (configs.containsKey("url")) {
            String url = (String) configs.get("url");
            Object userName = configs.get("username");
            Object password = configs.get("password");
            Properties properties = new Properties();
            if (userName != null) {
                properties.put("user", String.valueOf(userName));
            }
            if (password != null) {
                properties.put("password", String.valueOf(password));
            }
            if (datasource != null) {
                properties.put("datasource", datasource);
            }

            if (StringUtil.startsWithIgnoreCase(url, Driver.URL_PREFIX)) {
                init(url, properties);
            }
        } else {
            for (Object key : configs.keySet()) {
                Object value = configs.get(key);
                if (value instanceof Map) {
                    init(String.valueOf(key), (Map) value);
                }
            }
        }
    }

    public static void init(String url, Properties info) {
        ClientConfig clientConfig = parseURL(url, info);
        HighLevelClients.getInstance().build(clientConfig);
    }

    public static ClientConfig parseURL(String url, Properties info) {
        if (info == null) {
            info = new Properties();
        }

        if (!StringUtil.startsWithIgnoreCase(url, Driver.URL_PREFIX)) {
            throw new SystemException(StringUtil.format("{} format error", url));
        }

        ClientConfig clientConfig = new ClientConfig();
        int index = url.indexOf('?');
        String urlString = url.substring(Driver.URL_PREFIX.length(), index);
        String[] values = StringUtil.split('/', urlString);
        if (values.length != 2) {
            throw new SystemException(StringUtil.format("{} format error", url));
        }

        clientConfig.setConnectionString(values[0]);
        clientConfig.setCatalog(values[1]);

        String queryString = url.substring(index + 1);
        String[] queryStringArray = StringUtil.split('&', queryString);
        for (String pair : queryStringArray) {
            String[] kv = StringUtil.split('=', pair);
            if (kv.length != 2) {
                throw new SystemException(StringUtil.format("{} format error", url));
            }
            info.put(kv[0], kv[1]);
        }

        clientConfig.setUserName(info.getProperty("user"));
        clientConfig.setPassword(info.getProperty("password"));
        clientConfig.setDataSource(info.getProperty("datasource"));
        clientConfig.setSchema(info.getProperty("schema"));

        return clientConfig;
    }
}
