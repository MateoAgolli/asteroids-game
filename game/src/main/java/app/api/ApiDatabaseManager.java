package app.api;

import app.session.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiDatabaseManager {

    private static final ApiClient apiClient = new ApiClient();
    private static final Gson gson = new Gson();

    public static boolean register(String username, String password) {
        try {
            HttpResponse<String> response = apiClient.register(username, password);

            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean login(String username, String password) {
        try {
            String response = apiClient.login(username, password);
            JsonObject obj = gson.fromJson(response, JsonObject.class);

            if (obj != null && obj.has("id") && obj.has("username")) {
                long userId = obj.get("id").getAsLong();
                String uname = obj.get("username").getAsString();

                SessionManager.saveUser(uname, userId);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionManager.clear();
        return false;
    }

    public static void insertHighScore(int score) {
        Long userId = SessionManager.getUserId();

        // Do nothing if user is not logged in
        if (userId == null) {
            return;
        }

        try {
            apiClient.submitScore(score, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getTopHighScores() {
        ArrayList<String> highScoresList = new ArrayList<>();

        try {
            String json = apiClient.getTopScores();
            JsonObject obj = gson.fromJson(json, JsonObject.class);

            if (obj == null || !obj.has("top")) {
                return highScoresList;
            }

            var topArray = obj.getAsJsonArray("top");
            int rank = 1;

            for (var elem : topArray) {
                JsonObject scoreObj = elem.getAsJsonObject();
                String username = scoreObj.get("username").getAsString();
                int score = scoreObj.get("score").getAsInt();

                highScoresList.add(rank + ". " + username + ": " + score + " points");
                rank++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highScoresList;
    }
}
