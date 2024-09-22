package com.czcuestc.sqles.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.StringUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ClientBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientBuilder.class);

    public static ElasticsearchClient buildClient(ClientConfig clientConfig) {
        RestClient restClient = RestClient.builder(getHosts(clientConfig)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

    public static RestHighLevelClient buildHighLevelClient(ClientConfig clientConfig) {
        RestClientBuilder builder = RestClient.builder(getHosts(clientConfig));

        // 连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(clientConfig.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(clientConfig.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(clientConfig.getRequestTimeout());
            return requestConfigBuilder;
        });

        // 连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setKeepAliveStrategy((response, context) -> clientConfig.getKeepAliveTime());
            httpClientBuilder.setMaxConnTotal(clientConfig.getMaxConnections());
            httpClientBuilder.setMaxConnPerRoute(clientConfig.getMaxConnPerRoute());
            httpClientBuilder.setDefaultCredentialsProvider(getCredentialsProvider(clientConfig));

            if (clientConfig.getSchema().equalsIgnoreCase("https")) {
                try {
                    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
                    SSLIOSessionStrategy sslStrategy = new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);
                    httpClientBuilder.setSSLContext(sslContext);
                    httpClientBuilder.setSSLStrategy(sslStrategy);
                    httpClientBuilder.disableAuthCaching();
                } catch (Exception e) {
                    LOGGER.error("config SSL error", e);
                    throw new EsException("config SSL error", e);
                }
            }
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }

    private static CredentialsProvider getCredentialsProvider(ClientConfig clientConfig) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(clientConfig.getUserName(), clientConfig.getPassword()));
        return credentialsProvider;
    }

    private static HttpHost[] getHosts(ClientConfig clientConfig) {
        String[] hosts = clientConfig.getConnectionString().split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int index = 0; index < hosts.length; index++) {
            String address = hosts[index];
            String[] kv = StringUtil.split(':', address);
            String host = kv[0];
            String port = kv[1];
            httpHosts[index] = new HttpHost(host, Integer.parseInt(port), clientConfig.getSchema());
        }

        return httpHosts;
    }
}
