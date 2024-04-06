package org.example.api.common.exception.model;

public record ErrorResponse<T>(
        String code,
        String message,
        T data
) {


    public static <T> ErrorResponse<T> error(String status, String message, T data) {
        return new ErrorResponse<>(status, message, data);
    }
}
