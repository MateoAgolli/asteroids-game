package com.asteroids.backend.dto;

import java.util.List;

public record LeaderboardResponse(
        List<LeaderboardEntry> top,
        LeaderboardEntry currentUser
) {
}
