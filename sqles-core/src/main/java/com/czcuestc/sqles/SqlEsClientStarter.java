package com.czcuestc.sqles;

import com.czcuestc.sqles.conf.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */

public class SqlEsClientStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlEsClientStarter.class);

    private static String[] basePackages;

    public static String[] getBasePackages() {
        return basePackages;
    }

    public static void setBasePackages(String[] basePackages) {
        SqlEsClientStarter.basePackages = basePackages;
    }

    public static void start() {
        ConfigLoader.load();
    }
}
