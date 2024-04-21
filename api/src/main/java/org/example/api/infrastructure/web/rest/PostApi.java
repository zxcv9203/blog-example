package org.example.api.infrastructure.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.common.config.model.UserSession;
import org.example.common.post.request.PostCreate;
import org.example.common.post.request.PostEdit;
import org.example.common.post.request.PostSearch;
import org.example.common.post.response.PostResponse;
import org.example.core.application.PostService;
import org.example.core.common.exception.InsufficientPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @GetMapping("/test")
    public String test(
            UserSession session
    ) {
        return String.valueOf(session.id());
    }

    @GetMapping("foo")
    public String bar() {
        return "인증이 필요없는 페이지";
    }

    @PostMapping("/posts")
    public ResponseEntity<Void> post(
            @RequestBody @Valid PostCreate request
    ) {
        postService.write(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.get(id));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getList(PostSearch request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getList(request));
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<Void> edit(
            @PathVariable Long id,
            @RequestBody @Valid PostEdit request
    ) {
        postService.edit(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
