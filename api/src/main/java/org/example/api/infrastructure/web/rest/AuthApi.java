package org.example.api.infrastructure.web.rest;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.common.auth.response.SessionResponse;
import org.example.core.application.auth.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<SessionResponse> login(
            @RequestBody LoginRequest request
    ) {
        SessionResponse response = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("SESSION", response.accessToken())
                .domain("localhost") // TODO 서버 환경에 따른 분리 필요
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        System.out.println(">>>>>>>>> cookie = " + cookie);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }
}

