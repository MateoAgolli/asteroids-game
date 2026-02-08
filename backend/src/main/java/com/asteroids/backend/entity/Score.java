package com.asteroids.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "high_scores")
public class Score {

    @Id
    @JsonProperty("userId")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private int score;

    public Score() {
    }

    public Score(User user, int score) {
        this.user = user;
        this.userId = user.getId();
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
