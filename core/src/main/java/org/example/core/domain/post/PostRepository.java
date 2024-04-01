package org.example.core.domain.post;

import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);

    long count();

    void deleteAll();
}
