package com.asteroids.backend.controller;

import com.asteroids.backend.dto.LeaderboardResponse;
import com.asteroids.backend.dto.ScoreRequest;
import com.asteroids.backend.service.ScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public void submitScore(@RequestBody ScoreRequest request) {
        scoreService.submitScore(request);
    }

    @GetMapping("/leaderboard")
    public LeaderboardResponse leaderboard(@RequestParam(required = false) Long userId) {
        return scoreService.getLeaderboard(userId);
    }
}
