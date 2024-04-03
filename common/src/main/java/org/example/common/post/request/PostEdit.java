package org.example.common.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostEdit(
        @NotBlank(message = "title 값은 필수값입니다.")
        String title,
        @NotBlank(message = "content 값은 필수값입니다.")
        String content
) {
}
