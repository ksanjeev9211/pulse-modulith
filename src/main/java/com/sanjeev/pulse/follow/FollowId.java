package com.sanjeev.pulse.follow;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
class FollowId implements Serializable {

    private Long followerId;
    private Long followeeId;

    protected FollowId() {}

    FollowId(Long followerId, Long followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    Long getFollowerId() { return followerId; }
    Long getFolloweeId() { return followeeId; }
}
