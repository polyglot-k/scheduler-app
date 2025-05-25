package com.example.scheduler.lv6.dto.common;

public record SuccessResponse<T>(
        String message,
        T data
) {
    public static <T> SuccessResponse<T> of(String message, T data) {
        return new SuccessResponse<>(message, data);
    }
}