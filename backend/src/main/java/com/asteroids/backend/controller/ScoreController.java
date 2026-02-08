package com.asteroids.backend.controller;

import com.asteroids.backend.dto.*;
import com.asteroids.backend.entity.*;
import com.asteroids.backend.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public ScoreController(ScoreRepository s, UserRepository u) {
        this.scoreRepository = s;
        this.userRepository = u;
    }

    @PostMapping
    public void submitScore(@RequestBody ScoreRequest request) {
        // Ignore guest scores
        if (request.userId() == null) {
            return;
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Find existing score
        Optional<Score> optionalScore = scoreRepository.findById(user.getId());

        if (optionalScore.isPresent()) {
            Score existing = optionalScore.get();
            if (request.score() > existing.getScore()) {
                existing.setScore(request.score());
                scoreRepository.save(existing);
            }
        } else {
            Score newScore = new Score();
            newScore.setScore(request.score());
            newScore.setUser(user);
            scoreRepository.save(newScore);
        }
    }

    @GetMapping("/leaderboard")
    public LeaderboardResponse leaderboard(@RequestParam(required = false) Long userId) {
        List<Score> top = scoreRepository.findTop100(PageRequest.of(0, 100));
        List<LeaderboardEntry> entries = new ArrayList<>();

        int rank = 1;
        for (Score s : top) {
            entries.add(new LeaderboardEntry(
                    rank++, s.getUser().getUsername(), s.getScore()
            ));
        }

        LeaderboardEntry current = null;

        if (userId != null) {
            Score score = scoreRepository.findById(userId).orElse(null);

            if (score != null) {
                current = new LeaderboardEntry(
                        scoreRepository.findRankByScore(score.getScore()),
                        score.getUser().getUsername(),
                        score.getScore()
                );
            } else {
                User user = userRepository.findById(userId).orElse(null);

                if (user != null) {
                    current = new LeaderboardEntry(0, user.getUsername(), 0);
                }
            }
        }

        return new LeaderboardResponse(entries, current);
    }
}