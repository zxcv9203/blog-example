package org.example.api.infrastructure.web.rest;

import jakarta.validation.Valid;
import org.example.api.infrastructure.web.rest.request.PostCreate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
