package com.sanjeev.pulse.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotNull Long authorId,
        @NotBlank @Size(max = 280) String text
) {}
