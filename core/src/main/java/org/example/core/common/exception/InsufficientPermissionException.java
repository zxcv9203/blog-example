package org.example.core.common.exception;

public class InsufficientPermissionException extends AuthorizationException {
    public InsufficientPermissionException(String message) {
        super(message);
    }

    protected InsufficientPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getStatusCode() {
        return "403";
    }
}
