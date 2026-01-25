package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.post.api.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "pulse.feed.fanout.async", havingValue = "true")
class FeedOnPostCreatedAsyncListener {

    private final FeedFanoutService fanoutService;

    @Async("feedFanoutExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(PostCreatedEvent event) {
        fanoutService.fanout(event, "async");
    }
}
