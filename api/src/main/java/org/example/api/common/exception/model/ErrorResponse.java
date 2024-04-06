package org.example.api.common.exception.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse<T>(
        String code,
        String message,
        T data
) {

    public static <T> ErrorResponse<T> error(HttpStatus status, String message, T data) {
        return new ErrorResponse<>(String.valueOf(status.value()), message, data);
    }

    public static <T> ErrorResponse<T> error(HttpStatus status, String message) {
        return new ErrorResponse<>(String.valueOf(status.value()), message, null);
    }
}
