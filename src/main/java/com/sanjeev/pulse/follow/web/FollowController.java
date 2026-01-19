package com.sanjeev.pulse.follow.web;

import com.sanjeev.pulse.follow.FollowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/follows")
public class FollowController {

    private final FollowService service;

    FollowController(FollowService service) {
        this.service = service;
    }

    @PostMapping
    void follow(@RequestBody FollowRequest req) {
        service.follow(req.followerId(), req.followeeId());
    }

    @DeleteMapping
    void unfollow(@RequestBody FollowRequest req) {
        service.unfollow(req.followerId(), req.followeeId());
    }


}
