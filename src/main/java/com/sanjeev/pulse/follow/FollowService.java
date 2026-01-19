package com.sanjeev.pulse.follow;

import com.sanjeev.pulse.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class FollowService {

    private final FollowRepository repo;
    private final UserService userService;

    FollowService(FollowRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @Transactional
    public void follow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }

        userService.requireUser(followerId);
        userService.requireUser(followeeId);

        if (repo.existsByIdFollowerIdAndIdFolloweeId(followerId, followeeId)) return;

        repo.save(new FollowEdge(followerId, followeeId, Instant.now()));
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        repo.deleteById(new FollowId(followerId, followeeId));
    }
}
