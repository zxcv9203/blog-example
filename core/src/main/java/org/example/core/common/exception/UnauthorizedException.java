package org.example.core.common.exception;

public class UnauthorizedException extends AuthorizationException{
    public UnauthorizedException(String message) {
        super(message);
    }

}
