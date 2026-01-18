package com.sanjeev.pulse.user;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_handle", columnNames = "handle")
})
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String handle;

    @Column(nullable = false, length = 80)
    private String displayName;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected User() { }

    public User(String handle, String displayName) {
        this.handle = handle;
        this.displayName = displayName;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
    }


}
