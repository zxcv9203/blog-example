package org.example.api.infrastructure.web.rest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.common.auth.response.SessionResponse;
import org.example.core.application.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<SessionResponse> login(
            @RequestBody LoginRequest request
    ) {
        Long userId = authService.login(request);

        byte[] keyBytes = Decoders.BASE64.decode("c3VwZXJsb25nc3RyaW5nc2VjcmV0a2V5LS0tLWFzZGFzZGFz");
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(secretKey)
                .compact();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SessionResponse(jws));
    }
}

