package com.sanjeev.pulse.feed;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

interface FeedRepository extends JpaRepository<FeedItem, Long> {

    @Query("""
        select f from FeedItem f
        where f.authorId = :userId
        order by f.createdAt desc, f.postId desc
    """)
    List<FeedItem> firstPage(Long userId, Pageable pageable);

    @Query("""
        select f from FeedItem f
        where f.authorId = :userId
          and (f.createdAt < :createdAt
               or (f.createdAt = :createdAt and f.postId < :postId))
        order by f.createdAt desc, f.postId desc
    """)
    List<FeedItem> pageAfter(
            Long userId,
            Instant createdAt,
            Long postId,
            Pageable pageable
    );
}
