package com.sanjeev.pulse.feed.web;

import java.time.Instant;

public record FeedItemView(
        Long postId,
        String text,
        Long authorId,
        String authorName,
        Instant createdAt
) {}
