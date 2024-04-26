package org.example.core.domain.member.exception;

import org.example.core.common.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
    private static final String MESSAGE = "멤버를 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
