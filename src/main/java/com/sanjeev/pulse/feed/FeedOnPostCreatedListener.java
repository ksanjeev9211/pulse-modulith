package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.follow.api.FollowQueryService;
import com.sanjeev.pulse.post.api.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
class FeedOnPostCreatedListener {

    private final FeedRepository feedRepository;
    private final FollowQueryService followQueryService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(PostCreatedEvent event) {

        long startNanos = System.nanoTime();

        List<Long> followerIds = new ArrayList<>(followQueryService.findFollowerIdsOf(event.authorId()));
        followerIds.add(event.authorId());

        int inserted = 0;
        for (Long userId : followerIds) {
            if (feedRepository.existsByIdUserIdAndIdPostId(userId, event.postId())) {
                continue;
            }
            feedRepository.save(new FeedItem(userId, event.postId(), event.authorId(), event.authorName(), event.createdAt()));
            inserted++;
        }

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
        log.info(
                "fanout-on-write postId={} authorId={} targets={} inserted={} tookMs={}",
                event.postId(),
                event.authorId(),
                followerIds.size(),
                inserted,
                tookMs
        );
    }
}
