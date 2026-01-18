package com.sanjeev.pulse.post.dto;

import java.time.Instant;

public record PostResponse(Long postId,
                           Long authorId,
                           String text,
                           Instant createdAt) {
}
