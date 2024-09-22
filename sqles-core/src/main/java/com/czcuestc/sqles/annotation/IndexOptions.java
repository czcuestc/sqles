package com.czcuestc.sqles.annotation;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public enum IndexOptions {
    NONE("NONE"),
    /**
     * Only documents are indexed: term frequencies and positions are omitted.
     * Phrase and other positional queries on the field will throw an exception, and scoring
     * will behave as if any term in the document appears only once.
     */
    DOCS("docs"),
    /**
     * Only documents and term frequencies are indexed: positions are omitted.
     * This enables normal scoring, except Phrase and other positional queries
     * will throw an exception.
     */
    FREQS("freqs"),
    /**
     * Indexes documents, frequencies and positions.
     * This is a typical default for full-text search: full scoring is enabled
     * and positional queries are supported.
     */
    POSITIONS("positions"),
    /**
     * Indexes documents, frequencies, positions and offsets.
     * Character offsets are encoded alongside the positions.
     */
    OFFSETS("offsets");

    private String options;

    IndexOptions(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }
}

