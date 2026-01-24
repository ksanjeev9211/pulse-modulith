package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.feed.web.FeedItemView;
import com.sanjeev.pulse.feed.web.FeedPageResponse;
import com.sanjeev.pulse.post.api.PostQueryService;
import com.sanjeev.pulse.post.api.PostView;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FeedQueryService {

    private final FeedRepository feedRepo;
    private final PostQueryService postQueryService;

    FeedQueryService(FeedRepository feedRepo, PostQueryService postQueryService) {
        this.feedRepo = feedRepo;
        this.postQueryService = postQueryService;
    }

    @Transactional(readOnly = true)
    public FeedPageResponse listUserFeed(Long userId, int limit, String cursor) {

        int pageSize = Math.min(Math.max(limit, 1), 100);

        List<FeedItem> feedItems;
        if (cursor == null || cursor.isBlank()) {
            feedItems = feedRepo.firstPage(userId, PageRequest.of(0, pageSize));
        } else {
            Cursor c = Cursor.parse(cursor);
            feedItems = feedRepo.pageAfter(userId, c.createdAt(), c.postId(), PageRequest.of(0, pageSize));
        }

        // Next cursor based on deterministic ordering
        String nextCursor = feedItems.isEmpty() ? null : Cursor.of(feedItems.get(feedItems.size() - 1));

        // --- Hydration step (book-aligned) ---
        // 1) get post IDs from feed store
        List<Long> postIds = feedItems.stream()
                .map(feedItem -> feedItem.getId().getPostId())
                .toList();

        // 2) fetch post objects from post service (via post :: api)
        List<PostView> posts = postQueryService.findPostsByIds(postIds);

        // 3) build lookup map for composition
        Map<Long, PostView> postById = posts.stream()
                .collect(Collectors.toMap(PostView::postId, Function.identity()));

        // 4) compose final hydrated response items in feed order
        List<FeedItemView> items = new ArrayList<>(feedItems.size());
        for (FeedItem feed : feedItems) {
            PostView post = postById.get(feed.getId().getPostId());

            // If a post is missing (deleted, inconsistent, etc.), skip for now.
            // (We’ll handle data consistency strategies later when the book introduces them.)
            if (post == null) continue;

            items.add(new FeedItemView(
                    feed.getId().getPostId(),
                    post.text(),
                    feed.getAuthorId(),
                    feed.getAuthorName(),
                    feed.getCreatedAt()
            ));
        }

        return new FeedPageResponse(items, nextCursor);
    }

    /**
     * Cursor format: <epochMillis>_<postId>
     * Example: 1737138123456_101
     */
    private record Cursor(Instant createdAt, Long postId) {

        static Cursor parse(String cursor) {
            String[] parts = cursor.split("_", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid cursor format. Expected <epochMillis>_<postId>");
            }
            long epochMillis = Long.parseLong(parts[0]);
            long postId = Long.parseLong(parts[1]);
            return new Cursor(Instant.ofEpochMilli(epochMillis), postId);
        }

        static String of(FeedItem item) {
            return item.getCreatedAt().toEpochMilli() + "_" + item.getId().getPostId();
        }
    }
}
