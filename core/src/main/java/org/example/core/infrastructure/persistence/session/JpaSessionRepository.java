package org.example.core.infrastructure.persistence.session;

import org.example.core.domain.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSessionRepository extends JpaRepository<Session, Long> {

}
