package org.example.core.infrastructure.persistence.session;

import org.example.core.domain.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByAccessToken(String accessToken);
}
