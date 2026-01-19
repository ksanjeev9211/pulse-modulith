package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.feed.web.FeedItemView;
import com.sanjeev.pulse.feed.web.FeedPageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FeedQueryService {

    private final FeedRepository repo;

    FeedQueryService(FeedRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public FeedPageResponse list(Long userId, int limit, String cursor) {

        int pageSize = Math.min(Math.max(limit, 1), 100);

        List<FeedItem> page;
        if (cursor == null || cursor.isBlank()) {
            page = repo.firstPage(userId, PageRequest.of(0, pageSize));
        } else {
            Cursor c = Cursor.parse(cursor);
            page = repo.pageAfter(userId, c.createdAt(), c.postId(), PageRequest.of(0, pageSize));
        }

        var items = page.stream()
                .map(f -> new FeedItemView(
                        f.getPostId(),
                        f.getAuthorId(),
                        f.getAuthorName(),
                        f.getCreatedAt()
                ))
                .toList();

        String next = page.isEmpty() ? null : Cursor.of(page.getLast());

        return new FeedPageResponse(items, next);
    }

    record Cursor(Instant createdAt, Long postId) {
        static Cursor parse(String cursor) {
            String[] p = cursor.split("_", 2);
            if (p.length != 2) throw new IllegalArgumentException("Invalid cursor format");
            return new Cursor(Instant.ofEpochMilli(Long.parseLong(p[0])), Long.parseLong(p[1]));
        }
        static String of(FeedItem item) {
            return item.getCreatedAt().toEpochMilli() + "_" + item.getPostId();
        }
    }
}
