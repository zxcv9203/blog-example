package org.example.core.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);

    void deleteAll();
}
