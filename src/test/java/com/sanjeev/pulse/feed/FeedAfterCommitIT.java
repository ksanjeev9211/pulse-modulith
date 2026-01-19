package com.sanjeev.pulse.feed;

import com.sanjeev.pulse.post.PostService;
import com.sanjeev.pulse.post.dto.CreatePostRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class FeedAfterCommitIT {

    @Autowired FailingFacade failingFacade;
    @Autowired FeedRepository feedRepository;

    @Test
    void rollbackPreventsFeedUpdate() {
        assertThatThrownBy(() -> failingFacade.createThenFail())
                .isInstanceOf(RuntimeException.class);

        assertThat(feedRepository.count()).isZero();
    }

    @TestConfiguration
    static class Config {
        @Bean
        FailingFacade failingFacade(PostService postService) {
            return new FailingFacade(postService);
        }
    }

    static class FailingFacade {
        private final PostService postService;

        FailingFacade(PostService postService) {
            this.postService = postService;
        }

        @Transactional
        public void createThenFail() {
            postService.create(new CreatePostRequest(1L, "boom"));
            throw new RuntimeException("force rollback");
        }
    }
}
