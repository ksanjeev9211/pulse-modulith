package com.sanjeev.pulse.feed;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
class FeedItem {

    @EmbeddedId
    private FeedItemId id;     // idempotency + simplicity

    private Long authorId;
    private String authorName;
    private Instant createdAt;

    FeedItem(Long userId, Long postId, Long authorId, String authorName, Instant createdAt) {
        this.id = new FeedItemId(userId, postId);
        this.authorId = authorId;
        this.authorName = authorName;
        this.createdAt = createdAt;
    }




}

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
class FeedItemId {
    private Long userId;
    private Long postId;
}
