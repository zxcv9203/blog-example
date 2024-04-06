package org.example.core.common.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final Map<String, String> validation = new HashMap<>();

    protected BusinessException(String message) {
        super(message);
    }

    protected BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getStatusCode();

    public void addValidation(String field, String message) {
        validation.put(field, message);
    }
}
