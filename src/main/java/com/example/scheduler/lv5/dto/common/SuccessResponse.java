package com.example.scheduler.lv5.dto.common;

public record SuccessResponse<T>(
        int status,
        String message,
        T data
) {
    public static <T> SuccessResponse<T> of(int status, String message, T data) {
        return new SuccessResponse<>(status, message, data);
    }
}