package com.sanjeev.pulse.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

 interface PostRepository extends JpaRepository<Post, Long> {

    // First page (no cursor)
    @Query("""
        select p from Post p
        where p.authorId = :authorId
        order by p.createdAt desc, p.id desc
    """)
    List<Post> firstPage(Long authorId, Pageable pageable);

    // Next pages (cursor)
    @Query("""
        select p from Post p
        where p.authorId = :authorId
          and (p.createdAt < :createdAt or (p.createdAt = :createdAt and p.id < :id))
        order by p.createdAt desc, p.id desc
    """)
    List<Post> pageAfter(Long authorId, Instant createdAt, Long id, Pageable pageable);
}
