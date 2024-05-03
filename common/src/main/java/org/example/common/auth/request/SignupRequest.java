package org.example.common.auth.request;

import lombok.Builder;

@Builder
public record SignupRequest(
        String email,
        String name,
        String password
) {

}
