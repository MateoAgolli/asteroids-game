package com.asteroids.backend.controller;

import com.asteroids.backend.entity.Score;
import com.asteroids.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository repository;

    @PostMapping
    public Score submitScore(@RequestBody Score score) {
        return repository.save(score);
    }

    // Get top 10 scores
    @GetMapping("/top")
    public List<Score> getTopScores() {
        return repository.findTop10ByOrderByScoreDesc();
    }
}