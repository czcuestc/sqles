package com.sqles.demo.http;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -113260482371254838L;
    private int code;

    private String message;

    private T data;

    protected Response() {
    }

    protected Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Response<T> success() {
        return new Response<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> Response<T> success(T data, String message) {
        return new Response<T>(ErrorCode.SUCCESS.getCode(), message, data);
    }

    public static <T> Response<T> failed(ErrorCode errorCode) {
        return new Response<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Response<T> failed(String message) {
        return new Response<T>(ErrorCode.FAILED.getCode(), message, null);
    }

    public static <T> Response<T> failed() {
        return failed(ErrorCode.FAILED);
    }

    public static <T> Response<T> validateFailed() {
        return failed(ErrorCode.VALIDATE_FAILED);
    }

    public static <T> Response<T> unauthorized(T data) {
        return new Response<T>(ErrorCode.UNAUTHORIZED.getCode(), ErrorCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> Response<T> forbidden(T data) {
        return new Response<T>(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getMessage(), data);
    }
}
