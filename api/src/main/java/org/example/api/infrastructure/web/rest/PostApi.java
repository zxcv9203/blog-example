package org.example.api.infrastructure.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.post.request.PostCreate;
import org.example.core.application.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @GetMapping("/posts")
    public String get() {
        return "Hello World!";
    }

    @PostMapping("/posts")
    public ResponseEntity<Void> post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
