package Services;

public class Session {
    private static String userId;       // Student_ID or Warden_ID
    private static String userRole;     // "Student" or "Warden"
    private static String userPassword; // Password entered at login

    public static void setUser(String id, String role, String password) {
        userId = id;
        userRole = role;
        userPassword = password;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUserRole() {
        return userRole;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static boolean isStudent() {
        return "Student".equalsIgnoreCase(userRole);
    }

    public static boolean isWarden() {
        return "Warden".equalsIgnoreCase(userRole);
    }

    public static void clearSession() {
        userId = null;
        userRole = null;
        userPassword = null;
    }
}
