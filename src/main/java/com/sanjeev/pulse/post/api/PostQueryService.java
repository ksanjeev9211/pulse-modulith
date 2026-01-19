package com.sanjeev.pulse.post.api;

import java.util.Collection;
import java.util.List;

public interface PostQueryService {

    List<PostView> findPostsByIds(Collection<Long> postIds);
}
