package com.czcuestc.sqles.engine.select;

import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.util.StringUtil;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public enum MatchMode {
    NONE("none"),
    MATCH("match"),
    MATCH_PHRASE("match_phrase"),
    MULTI_MATCH("multi_match"),
    QUERY_STRING("query_string");

    private final String name;

    MatchMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MatchMode getMatchMode(String value) {
        if (StringUtil.isEmpty(value)) return NONE;
        String mode = value.toLowerCase();
        switch (mode) {
            case "match":
                return MATCH;
            case "match_phrase":
                return MATCH_PHRASE;
            case "multi_match":
                return MULTI_MATCH;
            case "query_string":
                return QUERY_STRING;
            default:
                throw new EsException(StringUtil.format("{} match mode invalid", value));
        }
    }
}