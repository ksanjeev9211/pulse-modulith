package com.sanjeev.pulse.user.web;

public record UserResponse(Long userId,
                           String handle,
                           String displayName) {
}
