package com.sanjeev.pulse.post.web;

import com.sanjeev.pulse.post.PostService;
import com.sanjeev.pulse.post.dto.PostResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users/{userId}/posts")
public class UserPostsController {

    private final PostService service;

    public UserPostsController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<PostResponse> list(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "50") int limit) {
        return service.listUserPosts(userId, limit);
    }
}
