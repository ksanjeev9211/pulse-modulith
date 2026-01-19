package com.sanjeev.pulse.follow;

import com.sanjeev.pulse.follow.web.FollowRequest;
import com.sanjeev.pulse.user.UserService;
import com.sanjeev.pulse.user.web.CreateUserRequest;
import com.sanjeev.pulse.user.web.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class FollowGraphIT {

    @Autowired
    RestTestClient restTestClient;

    @Autowired
    UserService userService;

    @Autowired
    FollowRepository followRepository;

    @Test
    void follow_createsEdge_and_unfollow_removesEdge() {
        // Arrange
        Long followerId = createUser("@Follower","follower").userId();
        Long followeeId = createUser("@Followee","followee").userId();

        // Act: follow
        restTestClient.post()
                .uri("/v1/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new FollowRequest(followerId, followeeId))
                .exchange()
                .expectStatus().isOk();

        // Assert: edge exists
        assertThat(followRepository
                .existsByIdFollowerIdAndIdFolloweeId(followerId, followeeId))
                .isTrue();

        List<Long> followers = followRepository.findFollowerIdsOf(followeeId);
        assertThat(followers).contains(followerId);

        List<Long> followees = followRepository.findFolloweeIdsOf(followerId);
        assertThat(followees).contains(followeeId);

        // Act: unfollow
        restTestClient.method(HttpMethod.DELETE)
                .uri("/v1/follows")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new FollowRequest(followerId, followeeId))
                .exchange()
                .expectStatus().isOk();

        // Assert: edge removed
        assertThat(followRepository
                .existsByIdFollowerIdAndIdFolloweeId(followerId, followeeId))
                .isFalse();
    }

    private UserResponse createUser(String handle, String displayName) {
        return userService.create(new CreateUserRequest(handle,displayName));
    }
}
