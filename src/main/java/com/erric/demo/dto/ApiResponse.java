package com.erric.demo.dto;

public class ApiResponse<T> {

    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}