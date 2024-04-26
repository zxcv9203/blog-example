package org.example.core.domain.auth;

public interface SessionRepository {

    long count();

    Session findById(Long id);

    Session findByAccessToken(String accessToken);
}
