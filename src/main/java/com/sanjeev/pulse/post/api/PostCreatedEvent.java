package com.sanjeev.pulse.post.api;

import java.time.Instant;

public record PostCreatedEvent(
        long postId,
        long authorId,
        String authorName,
        Instant createdAt
) {}
