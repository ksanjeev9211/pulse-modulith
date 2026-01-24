package com.sanjeev.pulse.feed;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

interface FeedRepository extends JpaRepository<FeedItem, FeedItemId> {

    @Query("""
        select f from FeedItem f
        where f.id.userId = :userId
        order by f.createdAt desc, f.id.postId desc
    """)
    List<FeedItem> firstPage(Long userId, Pageable pageable);

    @Query("""
        select f from FeedItem f
        where f.id.userId = :userId
          and (f.createdAt < :createdAt
               or (f.createdAt = :createdAt and f.id.postId < :postId))
        order by f.createdAt desc, f.id.postId desc
    """)
    List<FeedItem> pageAfter(
            Long userId,
            Instant createdAt,
            Long postId,
            Pageable pageable
    );

    boolean existsByIdUserIdAndIdPostId(Long userId, Long postId);
}
