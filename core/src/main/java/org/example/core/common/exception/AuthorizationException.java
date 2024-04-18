package org.example.core.common.exception;

public class AuthorizationException extends BusinessException {
    protected AuthorizationException(String message) {
        super(message);
    }

    protected AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getStatusCode() {
        return "401";
    }
}
