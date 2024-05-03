package org.example.core.domain.member.exception;

import org.example.core.common.exception.BusinessException;

public class DuplicateEmailException extends BusinessException {
    private static final String MESSAGE = "입력한 이메일은 이미 사용중입니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }
    @Override
    public String getStatusCode() {
        return "400";
    }
}
