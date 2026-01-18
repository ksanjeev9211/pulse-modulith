package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.post.api.PostCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
class FeedOnPostCreatedListener {

    private final FeedRepository feedRepository;

    FeedOnPostCreatedListener(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(PostCreatedEvent event) {
        if (feedRepository.existsById(event.postId())) return;
        feedRepository.save(new FeedItem(event.postId(), event.authorId(), event.createdAt()));
    }
}
