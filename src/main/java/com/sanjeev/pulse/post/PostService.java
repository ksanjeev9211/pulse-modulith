package com.sanjeev.pulse.post;

import com.sanjeev.pulse.post.api.PostCreatedEvent;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.post.dto.ListPostsResponse;
import com.sanjeev.pulse.post.dto.PostResponse;
import com.sanjeev.pulse.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repo;
    private final UserService userService;

    private final ApplicationEventPublisher events;


    @Transactional
    public PostResponse create(CreatePostRequest req) {
        userService.requireUser(req.authorId()); // ensure author exists
        var author = userService.getUser(req.authorId());
        Post saved = repo.save(new Post().setAuthorId(req.authorId()).setText(req.text().trim()));

        var event = new PostCreatedEvent(
                saved.getId(),
                saved.getAuthorId(),
                author.getDisplayName(),
                saved.getCreatedAt()

        );
        events.publishEvent(event);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ListPostsResponse listUserPosts(Long userId, int limit, String cursor) {
        userService.requireUser(userId); // optional, but consistent & user-friendly

        int pageSize = Math.min(Math.max(limit, 1), 100);

        List<Post> page;
        if (cursor == null || cursor.isBlank()) {
            page = repo.firstPage(userId, PageRequest.of(0, pageSize));
        } else {
            Cursor c = parseCursor(cursor);
            page = repo.pageAfter(userId, c.createdAt(), c.postId(), PageRequest.of(0, pageSize));
        }

        List<PostResponse> items = page.stream().map(PostService::toResponse).toList();
        String nextCursor = page.isEmpty() ? null : toCursor(page.get(page.size() - 1));

        return new ListPostsResponse(items, nextCursor);
    }

    private static PostResponse toResponse(Post p) {
        return new PostResponse(p.getId(), p.getAuthorId(), p.getText(), p.getCreatedAt());
    }

    /**
     * Cursor format: <epochMillis>_<postId>
     * Example: 1737138123456_101
     */
    private static Cursor parseCursor(String cursor) {
        String[] parts = cursor.split("_", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid cursor format. Expected <epochMillis>_<postId>");
        }
        long epochMillis = Long.parseLong(parts[0]);
        long postId = Long.parseLong(parts[1]);
        return new Cursor(Instant.ofEpochMilli(epochMillis), postId);
    }

    private static String toCursor(Post p) {
        return p.getCreatedAt().toEpochMilli() + "_" + p.getId();
    }

    private record Cursor(Instant createdAt, Long postId) {}
}
