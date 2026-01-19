package com.sanjeev.pulse.follow;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
class FollowEdge {

    @EmbeddedId
    private FollowId id;

    private Instant createdAt;

    protected FollowEdge() {}

    FollowEdge(Long followerId, Long followeeId, Instant createdAt) {
        this.id = new FollowId(followerId, followeeId);
        this.createdAt = createdAt;
    }

    FollowId getId() { return id; }
    Instant getCreatedAt() { return createdAt; }
}
