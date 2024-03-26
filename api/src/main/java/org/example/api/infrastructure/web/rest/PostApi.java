package org.example.api.infrastructure.web.rest;

import jakarta.validation.Valid;
import org.example.common.post.request.PostCreate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostApi {

    @GetMapping("/posts")
    public String get() {
        return "Hello World!";
    }

    @PostMapping("/posts")
    public String post(@RequestBody @Valid PostCreate request) {

        return "Hello World!";
    }
}
