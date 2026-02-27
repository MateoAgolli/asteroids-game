package com.asteroids.backend.service;

import com.asteroids.backend.dto.LeaderboardEntry;
import com.asteroids.backend.dto.LeaderboardResponse;
import com.asteroids.backend.dto.ScoreRequest;
import com.asteroids.backend.entity.Score;
import com.asteroids.backend.entity.User;
import com.asteroids.backend.repository.ScoreRepository;
import com.asteroids.backend.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public ScoreService(ScoreRepository scoreRepository, UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    public void submitScore(ScoreRequest request) {
        if (request.userId() == null) {
            return;
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

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

    public LeaderboardResponse getLeaderboard(Long userId) {
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
