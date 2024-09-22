package com.czcuestc.sqles.common.exceptions;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 6578211429934764273L;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable e) {
        super(e);
    }

    public SystemException(String message, Throwable e) {
        super(message, e);
    }
}
