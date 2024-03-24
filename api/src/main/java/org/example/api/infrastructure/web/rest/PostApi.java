package org.example.api.infrastructure.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostApi {

    @GetMapping("/posts")
    public String get() {
        return "Hello World!";
    }
}
