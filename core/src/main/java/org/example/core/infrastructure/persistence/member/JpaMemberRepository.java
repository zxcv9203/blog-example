package org.example.core.infrastructure.persistence.member;

import org.example.core.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
