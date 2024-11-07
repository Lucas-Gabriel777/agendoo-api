package com.technosdev.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class MainResource {
    @GetMapping
    public ResponseEntity<String> init() {
        return ResponseEntity.ok().body("technosdev 🔥");
    }
}
