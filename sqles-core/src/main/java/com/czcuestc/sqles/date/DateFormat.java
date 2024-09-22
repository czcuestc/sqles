package com.czcuestc.sqles.date;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public enum DateFormat {
    BASIC_DATE("yyyyMMdd"),
    BASIC_DATE_TIME("yyyyMMdd'T'HHmmssZ"),
    BASIC_DATE_TIME_MILLIS("yyyyMMdd'T'HHmmss.SSSZ"),
    STRICT_DATE("yyyy-MM-dd"),
    STRICT_DATE_TIME("yyyy-MM-dd'T'HH:mm:ssZ"),
    STRICT_DATE_TIME_MILLIS("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
    EPOCH_MILLIS("epoch_millis"),
    EPOCH_SECOND("epoch_second");

    private final String pattern;

    DateFormat(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @since 4.2
     */
    public String getPattern() {
        return pattern;
    }
}
