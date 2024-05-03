package org.example.core.infrastructure.persistence.member;

import lombok.RequiredArgsConstructor;
import org.example.core.domain.member.Member;
import org.example.core.domain.member.MemberRepository;
import org.example.core.domain.member.exception.DuplicateEmailException;
import org.example.core.domain.member.exception.MemberNotFoundException;
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

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public long count() {
        return memberRepository.count();
    }

    @Override
    public void checkByEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
