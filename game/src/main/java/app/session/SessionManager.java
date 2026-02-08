package app.session;

import java.util.prefs.Preferences;

public class SessionManager {

    private static final Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);

    // Save logged-in user
    public static void saveUser(String username, Long userId) {
        prefs.put("username", username);
        prefs.putLong("userId", userId);
    }

    public static String getUsername() {
        return prefs.get("username", null);
    }

    public static Long getUserId() {
        long id = prefs.getLong("userId", -1);
        return id == -1 ? null : id;
    }

    public static boolean isLoggedIn() {
        return getUserId() != null;
    }

    // Clear saved user (logout)
    public static void clear() {
        prefs.remove("username");
        prefs.remove("userId");
    }
}
