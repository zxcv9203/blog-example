package org.example.api.infrastructure.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.post.request.PostCreate;
import org.example.common.post.response.PostResponse;
import org.example.core.application.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Void> post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.get(id));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getList(Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getList(pageable));
    }
}
