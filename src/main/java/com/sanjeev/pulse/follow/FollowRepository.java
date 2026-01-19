package com.sanjeev.pulse.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface FollowRepository extends JpaRepository<FollowEdge, FollowId> {

    boolean existsByIdFollowerIdAndIdFolloweeId(Long followerId, Long followeeId);

    @Query("select f.id.followerId from FollowEdge f where f.id.followeeId = :followeeId")
    List<Long> findFollowerIdsOf(Long followeeId);

    @Query("select f.id.followeeId from FollowEdge f where f.id.followerId = :followerId")
    List<Long> findFolloweeIdsOf(Long followerId);
}
