package com.sanjeev.pulse.post.web;

import com.sanjeev.pulse.post.PostService;
import com.sanjeev.pulse.post.dto.ListPostsResponse;
import com.sanjeev.pulse.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users/{userId}/posts")
@RequiredArgsConstructor
public class UserPostsController {

    private final PostService service;

    @GetMapping
    public ListPostsResponse list(@PathVariable Long userId,
                                  @RequestParam(defaultValue = "20") int limit,
                                  @RequestParam(required = false) String cursor) {

        return service.listUserPosts(userId, limit, cursor);
    }
}
