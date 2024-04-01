package org.example.common.post.response;

import lombok.Builder;

@Builder
public record PostResponse(
        Long id,
        String title,
        String content
) {
}
