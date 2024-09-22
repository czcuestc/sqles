package com.czcuestc.sqles.common.exceptions;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class EsException extends RuntimeException {
    private static final long serialVersionUID = -9038566500940941414L;

    public EsException(String message) {
        super(message);
    }

    public EsException(Throwable e) {
        super(e);
    }

    public EsException(String message, Throwable e) {
        super(message, e);
    }
}
