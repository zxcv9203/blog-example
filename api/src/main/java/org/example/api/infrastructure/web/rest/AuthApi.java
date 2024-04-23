package org.example.api.infrastructure.web.rest;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.core.application.auth.AuthService;
import org.example.core.domain.member.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request
    ) {
        authService.login(request);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}

