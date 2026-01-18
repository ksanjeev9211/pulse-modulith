package com.sanjeev.pulse.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "posts", indexes = {
        @Index(name = "idx_posts_author_created_id", columnList = "authorId, createdAt, id")
})
@Getter
@Setter
@Accessors(chain = true)
class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false, length = 280)
    private String text;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;



    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
    }


}
