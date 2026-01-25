package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.follow.api.FollowQueryService;
import com.sanjeev.pulse.post.api.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
class FeedFanoutService {

    private final FeedRepository feedRepository;
    private final FollowQueryService followQueryService;

    FanoutMetrics fanout(PostCreatedEvent event, String mode) {
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
                "fanout-on-write mode={} postId={} authorId={} targets={} inserted={} tookMs={}",
                mode,
                event.postId(),
                event.authorId(),
                followerIds.size(),
                inserted,
                tookMs
        );

        return new FanoutMetrics(followerIds.size(), inserted, tookMs);
    }

    record FanoutMetrics(int targets, int inserted, long tookMs) {}
}
