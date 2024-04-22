package org.example.core.domain.member;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByEmailAndPassword(String email, String password);
}
