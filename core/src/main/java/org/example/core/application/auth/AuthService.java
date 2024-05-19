package org.example.core.application.auth;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.common.auth.request.SignupRequest;
import org.example.core.domain.member.Member;
import org.example.core.domain.member.MemberRepository;
import org.example.core.domain.member.exception.InvalidSigninException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Long login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new InvalidSigninException();
        }

        return member.getId();
    }

    public void signup(SignupRequest request) {
        memberRepository.checkByEmail(request.email());

        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = Member.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .createdAt(LocalDateTime.now())
                .build();

        memberRepository.save(member);
    }
}
