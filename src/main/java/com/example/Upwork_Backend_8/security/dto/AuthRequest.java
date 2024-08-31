package com.example.Upwork_Backend_8.security.dto;

import lombok.Builder;

@Builder
public record AuthRequest(
        String username,
        String password

) {

}
