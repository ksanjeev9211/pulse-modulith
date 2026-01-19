package com.sanjeev.pulse.post;

import com.sanjeev.pulse.post.api.PostQueryService;
import com.sanjeev.pulse.post.api.PostView;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository repo;

    PostQueryServiceImpl(PostRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<PostView> findPostsByIds(Collection<Long> postIds) {
        return repo.findAllById(postIds).stream()
                .map(p -> new PostView(
                        p.getId(),
                        p.getAuthorId(),
                        p.getText(),
                        p.getCreatedAt()
                ))
                .toList();
    }
}
