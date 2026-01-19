package com.sanjeev.pulse.feed.web;

import java.util.List;

public record FeedPageResponse(
        List<FeedItemView> items,
        String nextCursor
) {}
