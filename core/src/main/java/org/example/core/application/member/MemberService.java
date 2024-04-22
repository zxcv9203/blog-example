package org.example.core.application.member;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.request.LoginRequest;
import org.example.core.domain.member.Member;
import org.example.core.domain.member.MemberRepository;
import org.example.core.domain.member.exception.InvalidSigninException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member login(LoginRequest request) {
        return memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(InvalidSigninException::new);
    }
}
