package com.sanjeev.pulse.feed.web;

import com.sanjeev.pulse.feed.FeedQueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/{userId}/feed")
class FeedController {

    private final FeedQueryService service;

    FeedController(FeedQueryService service) {
        this.service = service;
    }

    @GetMapping
    FeedPageResponse list(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String cursor
    ) {
        return service.list(userId, limit, cursor);
    }
}
