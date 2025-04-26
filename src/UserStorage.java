import java.util.*;
import java.util.prefs.Preferences;

public class UserStorage {
    private static final Preferences prefs = Preferences.userNodeForPackage(UserStorage.class);
    private static final String USERS_KEY = "users";

    /**
     * Save a user to local storage
     * 
     * @param username Username
     * @param password Password
     * @param fullName Full name
     * @param email    Email
     * @param role     User role (admin, teacher, student)
     * @return true if saved successfully
     */
    public static boolean saveUser(String username, String password, String fullName,
            String email, String role) {
        try {
            List<Map<String, String>> users = getUsers();
            for (Map<String, String> user : users) {
                if (user.get("username").equals(username)) {
                    return false;
                }
            }

            Map<String, String> user = new HashMap<>();
            user.put("username", username);
            user.put("password", password);
            user.put("fullName", fullName);
            user.put("email", email);
            user.put("role", role);
            users.add(user);

            prefs.put(USERS_KEY, serializeUsers(users));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all users from local storage
     * 
     * @return List of user maps
     */
    public static List<Map<String, String>> getUsers() {
        try {
            String usersJson = prefs.get(USERS_KEY, "");
            if (usersJson.isEmpty()) {
                return new ArrayList<>();
            }
            return deserializeUsers(usersJson);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Authenticate a user
     * 
     * @param username Username
     * @param password Password
     * @return User data as Map if authenticated, null otherwise
     */
    public static Map<String, String> authenticateUser(String username, String password) {
        List<Map<String, String>> users = getUsers();

        for (Map<String, String> user : users) {
            if (user.get("username").equals(username) &&
                    user.get("password").equals(password)) {
                return user;
            }
        }

        return null;
    }

    public static void initDefaultUsers() {
        if (getUsers().isEmpty()) {
            // Create default admin user
            saveUser("admin", "admin", "System Administrator",
                    "admin@school.edu", "admin");

            // Create default teacher user
            saveUser("teacher", "teacher", "John Smith",
                    "teacher@school.edu", "teacher");

            // Create default student users
            saveUser("student1", "student1", "Alice Johnson",
                    "alice@student.edu", "student");
            saveUser("student2", "student2", "Bob Williams",
                    "bob@student.edu", "student");
        }
    }

    public static void clearUsers() {
        prefs.put(USERS_KEY, "");
    }

    private static String serializeUsers(List<Map<String, String>> users) {
        StringBuilder sb = new StringBuilder();

        for (Map<String, String> user : users) {
            sb.append(user.get("username")).append(";");
            sb.append(user.get("password")).append(";");
            sb.append(user.get("fullName")).append(";");
            sb.append(user.get("email")).append(";");
            sb.append(user.get("role")).append("\n");
        }

        return sb.toString();
    }

    private static List<Map<String, String>> deserializeUsers(String data) {
        List<Map<String, String>> users = new ArrayList<>();
        String[] lines = data.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty())
                continue;

            String[] parts = line.split(";");
            if (parts.length >= 5) {
                Map<String, String> user = new HashMap<>();
                user.put("username", parts[0]);
                user.put("password", parts[1]);
                user.put("fullName", parts[2]);
                user.put("email", parts[3]);
                user.put("role", parts[4]);
                users.add(user);
            }
        }

        return users;
    }
}