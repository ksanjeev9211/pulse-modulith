package com.sanjeev.pulse.post.dto;

import java.util.List;

public record ListPostsResponse(
        List<PostResponse> items,
        String nextCursor
) {}
