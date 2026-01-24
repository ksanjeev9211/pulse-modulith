package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.feed.web.FeedPageResponse;
import com.sanjeev.pulse.post.PostService;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import com.sanjeev.pulse.user.UserService;
import com.sanjeev.pulse.user.web.CreateUserRequest;
import com.sanjeev.pulse.user.web.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class FeedHydrationIT {

    @Autowired RestTestClient restTestClient;
    @Autowired
    UserService userService;
    @Autowired PostService postService;

    @Test
    void hydratedFeedContainsText() {
        // Arrange: ensure user exists
        UserResponse user = userService.create(new CreateUserRequest("@ksanjeev9211", "sanjeev"));

        // Arrange: create posts (publishes event -> feed item persisted)
        postService.create(new CreatePostRequest(user.userId(), "Event Test A"));
        postService.create(new CreatePostRequest(user.userId(), "Event Test B"));

        // Act
        String url = "/v1/users/" + user.userId() + "/feed?limit=10";

        FeedPageResponse resp = restTestClient.get()
                .uri(url)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedPageResponse.class)
                .returnResult()
                .getResponseBody();

        // Assert
        assertThat(resp).isNotNull();
        assertThat(resp.items()).isNotEmpty();
        assertThat(resp.items().get(0).text()).isNotBlank(); // hydration proof
    }
}
