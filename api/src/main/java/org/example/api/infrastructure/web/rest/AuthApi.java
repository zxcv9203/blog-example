package org.example.api.infrastructure.web.rest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.api.common.config.JwtConfig;
import org.example.common.auth.request.LoginRequest;
import org.example.common.auth.request.SignupRequest;
import org.example.common.auth.response.SessionResponse;
import org.example.core.application.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signup(
            @RequestBody SignupRequest request
    ) {
        authService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}

