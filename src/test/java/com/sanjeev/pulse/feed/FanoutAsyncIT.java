package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.feed.web.FeedPageResponse;
import com.sanjeev.pulse.follow.web.FollowRequest;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.user.web.CreateUserRequest;
import com.sanjeev.pulse.user.web.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "pulse.feed.fanout.async=true"
)
@AutoConfigureRestTestClient
@EnabledIfSystemProperty(named = "pulse.async", matches = "true")
class FanoutAsyncIT {

    @Autowired
    RestTestClient client;

    @Test
    void postEventuallyAppearsInFollowerFeed() {
        UserResponse author = createUser("async-author", "Async Author");
        UserResponse follower = createUser("async-follower", "Async Follower");

        follow(follower.userId(), author.userId());

        createPost(author.userId(), "hello async");

        FeedPageResponse feed = awaitFeedContainsText(follower.userId(), "hello async", Duration.ofSeconds(2));
        assertThat(feed).isNotNull();
    }

    private FeedPageResponse awaitFeedContainsText(Long userId, String text, Duration timeout) {
        long deadline = System.nanoTime() + timeout.toNanos();
        FeedPageResponse last = null;

        while (System.nanoTime() < deadline) {
            last = fetchFeed(userId);
            if (last != null && last.items() != null && last.items().stream().anyMatch(i -> text.equals(i.text()))) {
                return last;
            }

            // Fanout runs off-thread in this mode; polling is the simplest deterministic assertion here.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        assertThat(last)
                .as("Expected feed to eventually contain text '%s'", text)
                .isNotNull();
        assertThat(last.items())
                .as("Expected feed items to be present")
                .isNotNull();
        assertThat(last.items().stream().anyMatch(i -> text.equals(i.text())))
                .as("Expected feed to contain text '%s' within %s", text, timeout)
                .isTrue();

        return last;
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

    private void createPost(Long authorId, String text) {
        client.post()
                .uri("/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreatePostRequest(authorId, text))
                .exchange()
                .expectStatus().isCreated();
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
