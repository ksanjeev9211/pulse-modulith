package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.post.api.PostCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@Slf4j
class FeedOnPostCreatedListener {

    private final FeedRepository feedRepository;

    FeedOnPostCreatedListener(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(PostCreatedEvent event) {
        log.info("FEED EVENT RECEIVED postId={}", event.postId());
        boolean already = feedRepository.existsById(event.postId());
        log.info("FEED existsById({}) = {}", event.postId(), already);
        if (already) return;
        feedRepository.saveAndFlush(new FeedItem(event.postId(), event.authorId(), event.authorName(), event.createdAt()));
    }
}
