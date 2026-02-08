package com.asteroids.backend.dto;

public record LeaderboardEntry(
        int rank,
        String username,
        int score
) {
}
