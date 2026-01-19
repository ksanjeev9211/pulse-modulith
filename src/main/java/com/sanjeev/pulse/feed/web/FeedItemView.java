package com.sanjeev.pulse.feed.web;

import java.time.Instant;

public record FeedItemView(
        long postId,
        long authorId,
        String authorName,
        Instant createdAt
) {}
