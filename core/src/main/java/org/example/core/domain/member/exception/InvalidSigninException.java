package org.example.core.domain.member.exception;

import org.example.core.common.exception.BusinessException;

public class InvalidSigninException extends BusinessException {
    private static final String MESSAGE = "아이디 혹은 비밀번호가 올바르지 않습니다.";

    public InvalidSigninException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
