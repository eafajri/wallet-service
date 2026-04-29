package com.erric.demo.dto;

public class BaseResponse<T> {

    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}