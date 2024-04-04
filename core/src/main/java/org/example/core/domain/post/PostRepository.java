package org.example.core.domain.post;

import org.example.common.post.request.PostSearch;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);

    long count();

    void deleteAll();

    List<Post> findAll();

    List<Post> saveAll(List<Post> requestPosts);

    List<Post> findAll(PostSearch pageable);

    void delete(Post post);
}
