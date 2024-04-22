package org.example.core.infrastructure.persistence.member;

import lombok.RequiredArgsConstructor;
import org.example.core.domain.member.Member;
import org.example.core.domain.member.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository memberRepository;

    @Override
    public Optional<Member> findByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
