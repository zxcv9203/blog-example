package org.example.api.common.exception.model;

public record ErrorResponse<T>(
    String code,
    String message,
    T data
) {

}
