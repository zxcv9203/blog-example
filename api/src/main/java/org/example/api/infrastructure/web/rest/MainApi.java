package org.example.api.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainApi {

    @GetMapping
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("Hello, World!");
    }
}
