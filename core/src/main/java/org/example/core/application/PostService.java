package org.example.core.application;

import lombok.RequiredArgsConstructor;
import org.example.common.post.request.PostCreate;
import org.example.core.domain.post.Post;
import org.example.core.domain.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.title())
                .content(postCreate.content())
                .build();

        postRepository.save(post);
    }
}
