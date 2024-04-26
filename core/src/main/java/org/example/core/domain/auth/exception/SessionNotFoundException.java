package org.example.core.domain.auth.exception;

import org.example.core.common.exception.BusinessException;

public class SessionNotFoundException extends BusinessException {
    private static final String MESSAGE = "해당 세션을 찾을 수 없습니다.";

    public SessionNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
