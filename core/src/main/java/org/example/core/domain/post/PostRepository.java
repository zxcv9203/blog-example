package org.example.core.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);

    long count();

    void deleteAll();

    List<Post> findAll();

    List<Post> saveAll(List<Post> requestPosts);

    Page<Post> findAll(Pageable pageable);
}
