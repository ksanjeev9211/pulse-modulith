package com.sanjeev.pulse.post;

import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.post.dto.PostResponse;
import com.sanjeev.pulse.user.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repo;
    private final UserService userService;

    public PostService(PostRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @Transactional
    public PostResponse create(CreatePostRequest req) {
        // ensure author exists (keeps early correctness)
        userService.requireUser(req.authorId());

        Post saved = repo.save(new Post().setAuthorId(req.authorId()).setText(req.text().trim()));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> listUserPosts(Long userId, int limit) {
        userService.requireUser(userId);
        int boundedLimit = Math.min(Math.max(limit, 1), 100);

        return repo.findByAuthorIdOrderByCreatedAtDesc(userId, PageRequest.of(0, boundedLimit))
                .stream()
                .map(PostService::toResponse)
                .toList();
    }

    private static PostResponse toResponse(Post p) {
        return new PostResponse(p.getId(), p.getAuthorId(), p.getText(), p.getCreatedAt());
    }
}
