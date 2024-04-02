package org.example.core.infrastructure.persistence;

import org.example.core.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post, Long>, QueryDslPostRepository {
}
