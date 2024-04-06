package org.example.core.domain.post.exception;

import org.example.core.common.exception.BusinessException;

public class PostNotFoundException extends BusinessException {
    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
