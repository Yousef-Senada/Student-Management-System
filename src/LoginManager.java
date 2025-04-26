public class LoginManager {
    private static String username;
    private static String password;

    public static void setCredentials(String username, String password) {
        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            LoginManager.username = username;
            LoginManager.password = password;
        } else {
            System.out.println("Invalid credentials provided.");
        }
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static boolean hasCredentials() {
        return username != null && password != null &&
                !username.isEmpty() && !password.isEmpty();
    }

    public static void clearCredentials() {
        username = null;
        password = null;
    }
}
