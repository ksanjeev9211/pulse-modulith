package com.sanjeev.pulse.feed;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Entity
@Setter
@Accessors(chain = true)
class FeedItem {

    @Id
    private Long postId;      // idempotency + simplicity

    private Long authorId;
    private Instant createdAt;



}
