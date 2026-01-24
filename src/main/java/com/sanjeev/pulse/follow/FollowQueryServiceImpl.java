package com.sanjeev.pulse.follow;

import com.sanjeev.pulse.follow.api.FollowQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class FollowQueryServiceImpl implements FollowQueryService {

    private final FollowRepository repo;


    FollowQueryServiceImpl(FollowRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findFollowerIdsOf(Long followeeId) {
        return repo.findFollowerIdsOf(followeeId);
    }
}
