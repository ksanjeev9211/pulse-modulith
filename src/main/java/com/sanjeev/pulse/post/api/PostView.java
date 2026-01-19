package com.sanjeev.pulse.post.api;

import java.time.Instant;

public record PostView(
        long postId,
        long authorId,
        String text,
        Instant createdAt
) {}
