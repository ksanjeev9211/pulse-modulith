package com.sanjeev.pulse.post.web;

import com.sanjeev.pulse.post.PostService;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.post.dto.PostResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody CreatePostRequest request) {
        return service.create(request);
    }
}
