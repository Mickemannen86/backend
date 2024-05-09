package com.examensbe.backend.models.user;

public record RegisterRequest(
        String username,
        String password,
        String roles
) {
}
