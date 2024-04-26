package org.example.core.infrastructure.persistence.session;

import lombok.RequiredArgsConstructor;
import org.example.core.domain.auth.Session;
import org.example.core.domain.auth.SessionRepository;
import org.example.core.domain.auth.exception.SessionNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final JpaSessionRepository jpaSessionRepository;

    @Override
    public long count() {
        return jpaSessionRepository.count();
    }

    @Override
    public Session findById(Long id) {
        return jpaSessionRepository.findById(id)
                .orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public Session findByAccessToken(String accessToken) {
        return jpaSessionRepository.findByAccessToken(accessToken)
                .orElseThrow(SessionNotFoundException::new);
    }
}
