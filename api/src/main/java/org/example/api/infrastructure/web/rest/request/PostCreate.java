package org.example.api.infrastructure.web.rest.request;

import jakarta.validation.constraints.NotBlank;

public record PostCreate(
        @NotBlank(message = "title 값은 필수값입니다.")
        String title,

        @NotBlank(message = "content 값은 필수값입니다.")
        String content
) {
}
