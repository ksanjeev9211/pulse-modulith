package com.sanjeev.pulse.user.dto;

public record UserResponse(Long userId,
                           String handle,
                           String displayName) {
}
