package com.czcuestc.sqles.client.container;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.czcuestc.sqles.client.ClientConfig;
import com.czcuestc.sqles.common.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Clients {
    private static Clients instance = new Clients();

    private Map<String, ElasticsearchClient> clients = new ConcurrentHashMap<>();

    private ElasticsearchClient defaultClient;

    private Clients() {
    }

    public static Clients getInstance() {
        return instance;
    }

    public ElasticsearchClient getClient(String dataSource) {
        if (StringUtil.isEmpty(dataSource)) {
            return defaultClient;
        }
        return clients.get(dataSource);
    }

    public void putClient(String dataSource, ElasticsearchClient client) {
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

//        ElasticsearchClient client = ClientBuilder.buildClient(clientConfig);
//        putClient(clientConfig.getConnectionString(), client);
//        putClient(clientConfig.getDataSource(), client);
    }
}