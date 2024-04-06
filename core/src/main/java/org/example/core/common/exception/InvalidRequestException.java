package org.example.core.common.exception;

public class InvalidRequestException extends BusinessException{
    private static final String MESSAGE = "잘못된 요청입니다.";

    protected InvalidRequestException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
