package org.example.core.application.post;

import lombok.RequiredArgsConstructor;
import org.example.common.post.request.PostCreate;
import org.example.common.post.request.PostEdit;
import org.example.common.post.request.PostSearch;
import org.example.common.post.response.PostResponse;
import org.example.core.domain.post.Post;
import org.example.core.domain.post.PostEditor;
import org.example.core.domain.post.PostRepository;
import org.example.core.domain.post.exception.PostNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch request) {
        return postRepository.findAll(request).stream()
                .map(post ->
                        PostResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .build()
                )
                .toList();
    }

    public void edit(Long id, PostEdit request) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        PostEditor.PostEditorBuilder editor = post.toEditor();
        PostEditor postEditor = editor
                .title(request.title())
                .content(request.content())
                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);

    }
}
