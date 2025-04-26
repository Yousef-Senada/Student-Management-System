# Configuration Guide

This document outlines advanced configuration options for the Student Management System.

## Table of Contents

- [JVM Settings](#jvm-settings)
- [UI Customization](#ui-customization)
- [Storage Options](#storage-options)
- [Backup and Restore](#backup-and-restore)
- [Logging](#logging)

## JVM Settings

You can optimize the application's performance by adjusting the Java Virtual Machine (JVM) settings when launching the application.

### Memory Allocation

```bash
# Allocate minimum and maximum heap size
java -Xms256m -Xmx1024m -cp bin App
```

### Garbage Collection

```bash
# Use G1 garbage collector (recommended for Java 8+)
java -XX:+UseG1GC -cp bin App
```

### System Properties

```bash
# Set system properties for configuration
java -Dsms.debug=true -Dsms.config.path=/path/to/config.properties -cp bin App
```

## UI Customization

### Theme Customization

You can modify the UI theme by editing the color constants in the source files:

1. Open `src/LoginFrame.java`
2. Locate the color definitions:
   ```java
   private Color primaryColor = new Color(41, 128, 185);
   private Color secondaryColor = new Color(52, 152, 219);
   private Color accentColor = new Color(231, 76, 60);
   private Color textColor = new Color(236, 240, 241);
   ```
3. Change the RGB values to your preferred colors
4. Recompile the application

### Font Settings

To change the default fonts:

1. Open the relevant frame files (e.g., `LoginFrame.java`, `MainSystemFrame.java`)
2. Locate the font definitions:
   ```java
   private Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
   private Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
   private Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
   ```
3. Modify the font family, style, or size
4. Recompile the application

## Storage Options

The application currently uses Java's Preferences API for local storage. You can modify this behavior by editing `UserStorage.java`.

### File-based Storage

To switch to file-based storage:

1. Create a new implementation in `FileStorage.java`
2. Implement methods for reading and writing data to JSON or XML files
3. Update references in the application to use the new storage method

Example file structure for JSON storage:

```
/data
  /users.json
  /students.json
  /courses.json
  /enrollments.json
```

### Database Connection

For production use, you can implement database connectivity:

1. Add JDBC dependencies to your project
2. Create a `DatabaseManager.java` class to handle connections
3. Implement data access objects (DAOs) for each entity
4. Update the application to use database storage

Example database connection code:

```java
public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sms";
    private static final String USER = "root";
    private static final String PASS = "password";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    
    // Additional methods for database operations
}
```

## Backup and Restore

### Manual Backup

To manually backup user data from the Preferences API:

1. Export preferences to an XML file:
   ```java
   public static void exportPreferences(String filePath) throws Exception {
       FileOutputStream os = new FileOutputStream(filePath);
       Preferences prefs = Preferences.userNodeForPackage(UserStorage.class);
       prefs.exportSubtree(os);
       os.close();
   }
   ```

2. Restore from backup:
   ```java
   public static void importPreferences(String filePath) throws Exception {
       FileInputStream is = new FileInputStream(filePath);
       Preferences.importPreferences(is);
       is.close();
   }
   ```

## Logging

To enable detailed logging:

1. Add a logging framework like Log4j or java.util.logging to your project
2. Create a `Logger.java` utility class
3. Implement logging throughout the application

Example logger implementation:

```java
public class Logger {
    private static final java.util.logging.Logger LOGGER = 
        java.util.logging.Logger.getLogger("StudentManagementSystem");
    
    static {
        try {
            FileHandler fileHandler = new FileHandler("sms.log");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void info(String message) {
        LOGGER.info(message);
    }
    
    public static void warn(String message) {
        LOGGER.warning(message);
    }
    
    public static void error(String message, Throwable t) {
        LOGGER.severe(message);
        t.printStackTrace();
    }
}
```

Use the logger in your code:

```java
Logger.info("User logged in: " + username);
Logger.error("Failed to save user data", exception);
```

---

For more advanced configuration options, please refer to the inline code comments or contact the development team. 