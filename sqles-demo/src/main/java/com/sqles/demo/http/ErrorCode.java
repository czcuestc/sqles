package com.sqles.demo.http;

public enum ErrorCode {
    SUCCESS(200, "success"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    VALIDATE_FAILED(404, "validate failed"),
    FAILED(500, "failed");

    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
