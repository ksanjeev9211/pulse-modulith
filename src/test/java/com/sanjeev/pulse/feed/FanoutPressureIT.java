package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.feed.web.FeedPageResponse;
import com.sanjeev.pulse.follow.web.FollowRequest;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.post.dto.PostResponse;
import com.sanjeev.pulse.user.web.CreateUserRequest;
import com.sanjeev.pulse.user.web.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@EnabledIfSystemProperty(named = "pulse.pressure", matches = "true")
class FanoutPressureIT {

    private static final int FOLLOWER_COUNT = 1_000;

    @Autowired
    RestTestClient client;

    @Test
    void measuresPostLatencyUnderHighFanout() {
        UserResponse author = createUser("author", "Author");

        Long firstFollowerId = null;

        for (int i = 1; i <= FOLLOWER_COUNT; i++) {
            String handle = "f" + String.format("%04d", i);
            UserResponse follower = createUser(handle, "Follower " + i);
            if (firstFollowerId == null) {
                firstFollowerId = follower.userId();
            }
            follow(follower.userId(), author.userId());
        }

        long startNanos = System.nanoTime();
        PostResponse post = createPost(author.userId(), "hello from celebrity");
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);

        // This is the intentional "pain" signal: post latency includes synchronous AFTER_COMMIT fanout.
        System.out.println("followers=" + FOLLOWER_COUNT + " postRequestTookMs=" + tookMs + " postId=" + (post == null ? null : post.postId()));

        // Sanity check: at least one follower can see the post.
        assertThat(firstFollowerId).isNotNull();
        FeedPageResponse feed = fetchFeed(firstFollowerId);
        assertThat(feed).isNotNull();
        assertThat(feed.items()).isNotEmpty();
        assertThat(feed.items().stream().anyMatch(i -> "hello from celebrity".equals(i.text()))).isTrue();
    }

    private UserResponse createUser(String handle, String displayName) {
        return client.post()
                .uri("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateUserRequest(handle, displayName))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();
    }

    private void follow(Long followerId, Long followeeId) {
        client.post()
                .uri("/v1/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new FollowRequest(followerId, followeeId))
                .exchange()
                .expectStatus().isOk();
    }

    private PostResponse createPost(Long authorId, String text) {
        return client.post()
                .uri("/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreatePostRequest(authorId, text))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();
    }

    private FeedPageResponse fetchFeed(Long userId) {
        return client.get()
                .uri("/v1/users/{userId}/feed?limit=20", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedPageResponse.class)
                .returnResult()
                .getResponseBody();
    }
}
