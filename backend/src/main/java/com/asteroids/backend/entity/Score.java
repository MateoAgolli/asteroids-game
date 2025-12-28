package com.asteroids.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "high_scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    public Score() {
    }

    public Score(int score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}