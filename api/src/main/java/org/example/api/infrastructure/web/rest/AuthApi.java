package org.example.api.infrastructure.web.rest;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.core.application.member.MemberService;
import org.example.core.domain.member.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final MemberService memberService;

    @PostMapping("/auth/login")
    public ResponseEntity<Member> login(
            @RequestBody LoginRequest request
    ) {
        Member loginUser = memberService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginUser);
    }
}

