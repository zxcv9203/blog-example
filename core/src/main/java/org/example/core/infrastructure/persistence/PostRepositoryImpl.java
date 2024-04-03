package org.example.core.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.example.common.post.request.PostSearch;
import org.example.core.domain.post.Post;
import org.example.core.domain.post.PostRepository;
import org.example.core.infrastructure.persistence.JpaPostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public long count() {
        return jpaPostRepository.count();
    }

    @Override
    public void deleteAll() {
        jpaPostRepository.deleteAll();
    }

    @Override
    public List<Post> findAll() {
        return jpaPostRepository.findAll();
    }

    @Override
    public List<Post> saveAll(List<Post> requestPosts) {
        return jpaPostRepository.saveAll(requestPosts);
    }

    @Override
    public List<Post> findAll(PostSearch postSearch) {
        return jpaPostRepository.getList(postSearch);
    }
}
