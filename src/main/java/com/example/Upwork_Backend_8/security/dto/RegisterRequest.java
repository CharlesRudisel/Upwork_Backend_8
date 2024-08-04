package com.example.Upwork_Backend_8.security.dto;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String username,
        String email,
        String password
) {


}
