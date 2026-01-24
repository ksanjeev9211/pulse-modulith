package com.sanjeev.pulse.follow.api;

import java.util.List;

public interface FollowQueryService {

    List<Long> findFollowerIdsOf(Long followeeId);
}
