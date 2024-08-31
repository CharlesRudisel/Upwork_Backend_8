package com.example.Upwork_Backend_8.security.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        Long userId,         // Added userId
        String username,     // Added username
        String roles         // Added roles
) {
}
