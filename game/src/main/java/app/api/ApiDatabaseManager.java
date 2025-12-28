package app.api;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ApiDatabaseManager {

    private static final ApiClient apiClient = new ApiClient();
    private static final Gson gson = new Gson();

    public static void insertHighScore(int score) {
        try {
            apiClient.submitScore(score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getTopHighScores() {
        ArrayList<String> highScoresList = new ArrayList<>();
        try {
            String json = apiClient.getTopScores();
            // Parse JSON array of objects, assuming Score { "score": 1234 }
            Score[] scores = gson.fromJson(json, Score[].class);
            int rank = 1;
            for (Score s : scores) {
                highScoresList.add(rank + ". " + s.getScore() + " points");
                rank++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highScoresList;
    }

    // Inner class to map JSON
    private static class Score {
        private int score;

        public int getScore() { return score; }
    }
}
