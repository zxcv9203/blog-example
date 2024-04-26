package org.example.core.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository {

    long count();

    Session findById(Long id);
}
