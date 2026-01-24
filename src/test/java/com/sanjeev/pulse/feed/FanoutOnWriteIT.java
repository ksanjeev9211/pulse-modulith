package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.feed.web.FeedPageResponse;
import com.sanjeev.pulse.follow.web.FollowRequest;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.user.web.CreateUserRequest;
import com.sanjeev.pulse.user.web.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class FanoutOnWriteIT {

    @Autowired
    RestTestClient client;

    @Autowired
    FeedRepository feedRepository;

    @Test
    void postIsFannedOutToFollowersFeeds() {
        // Arrange: create users A, B, C
        UserResponse userA = createUser("usera", "User A");
        UserResponse userB = createUser("userb", "User B");
        UserResponse userC = createUser("userc", "User C");

        // B follows A, C follows A
        follow(userB.userId(), userA.userId());
        follow(userC.userId(), userA.userId());

        // Act: A creates a post (this should fan out AFTER_COMMIT into B and C feeds)
        createPost(userA.userId(), "Hello from A");

        // Assert: write amplification is visible (B, C, and A all get an entry)
        assertThat(feedRepository.count()).isEqualTo(3);

        // Assert: B feed contains A's post
        FeedPageResponse feedB = fetchFeed(userB.userId());
        assertThat(feedB).isNotNull();
        assertThat(feedB.items()).isNotEmpty();
        assertThat(feedB.items().stream().anyMatch(i ->
                "Hello from A".equals(i.text()) && userA.userId().equals(i.authorId())
        )).isTrue();

        // Assert: C feed contains A's post
        FeedPageResponse feedC = fetchFeed(userC.userId());
        assertThat(feedC).isNotNull();
        assertThat(feedC.items()).isNotEmpty();
        assertThat(feedC.items().stream().anyMatch(i ->
                "Hello from A".equals(i.text()) && userA.userId().equals(i.authorId())
        )).isTrue();

        // Assert: A also sees their own post
        FeedPageResponse feedA = fetchFeed(userA.userId());
        assertThat(feedA).isNotNull();
        assertThat(feedA.items()).isNotEmpty();
        assertThat(feedA.items().stream().anyMatch(i ->
                "Hello from A".equals(i.text()) && userA.userId().equals(i.authorId())
        )).isTrue();
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
