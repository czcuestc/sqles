package com.czcuestc.sqles.client.container;

import com.czcuestc.sqles.client.ClientBuilder;
import com.czcuestc.sqles.client.ClientConfig;
import com.czcuestc.sqles.common.util.StringUtil;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class HighLevelClients {
    private static HighLevelClients instance = new HighLevelClients();

    private Map<String, RestHighLevelClient> clients = new ConcurrentHashMap<>();

    private RestHighLevelClient defaultClient;

    private HighLevelClients() {
    }

    public static HighLevelClients getInstance() {
        return instance;
    }

    public RestHighLevelClient getClient(String dataSource) {
        if (StringUtil.isEmpty(dataSource)) {
            return defaultClient;
        }
        return clients.get(dataSource);
    }

    public void putClient(String dataSource, RestHighLevelClient client) {
        if (StringUtil.isEmpty(dataSource)) {
            if (defaultClient == null) defaultClient = client;
            return;
        }

        if (clients.containsKey(dataSource)) {
            throw new RuntimeException(StringUtil.format("{} duplicate mapper", dataSource));
        }

        if (defaultClient == null) defaultClient = client;

        clients.put(dataSource, client);
    }

    public int size() {
        return clients.size();
    }

    public void build(ClientConfig clientConfig) {
        if (clients.containsKey(clientConfig.getConnectionString()))
            return;

        Clients.getInstance().build(clientConfig);

        RestHighLevelClient client = ClientBuilder.buildHighLevelClient(clientConfig);
        putClient(clientConfig.getConnectionString(), client);
        putClient(clientConfig.getDataSource(), client);
    }
}