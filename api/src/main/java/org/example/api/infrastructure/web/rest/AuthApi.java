package org.example.api.infrastructure.web.rest;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.common.auth.response.SessionResponse;
import org.example.core.application.auth.AuthService;
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
    public ResponseEntity<SessionResponse> login(
            @RequestBody LoginRequest request
    ) {
        SessionResponse response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}

