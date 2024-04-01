package org.example.core.domain.post;

import lombok.RequiredArgsConstructor;
import org.example.core.infrastructure.persistence.JpaPostRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final JpaPostRepository jpaPostRepository;

    @Override
    public Post save(Post post) {
        return jpaPostRepository.save(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return jpaPostRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        jpaPostRepository.deleteAll();
    }
}
