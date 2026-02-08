package com.asteroids.backend.repository;

import com.asteroids.backend.entity.Score;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s ORDER BY s.score DESC")
    List<Score> findTop100(Pageable pageable);

    @Query("SELECT COUNT(s) + 1 FROM Score s WHERE s.score > :score")
    int findRankByScore(@Param("score") int score);
}
