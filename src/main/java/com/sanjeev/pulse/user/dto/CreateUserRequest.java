package com.sanjeev.pulse.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank @Size(min = 3, max = 32) String handle,
        @NotBlank @Size(min = 1, max = 80) String displayName ){
}
