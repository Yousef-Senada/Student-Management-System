# Getting Started with Student Management System

This guide provides step-by-step instructions to get the Student Management System up and running on your local machine.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 8 or higher
- Git (optional, for cloning the repository)
- A text editor or IDE (like IntelliJ IDEA, Eclipse, or VS Code)

## Installation Steps

### 1. Get the Code

**Option 1: Clone the repository (if you have Git installed)**

```bash
git clone https://github.com/yourusername/student-management-system.git
cd student-management-system
```

**Option 2: Download the source code**

- Download the ZIP file from the repository
- Extract it to a folder of your choice
- Open a terminal/command prompt and navigate to that folder

### 2. Compile the Application

From the project root directory, compile the Java source files:

```bash
# Create the bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files into the bin directory
javac -d bin src/*.java
```

### 3. Run the Application

After successful compilation, run the application:

```bash
java -cp bin App
```

### 4. Login to the System

Use one of the default user accounts:

| Role     | Username | Password |
|----------|----------|----------|
| Admin    | admin    | admin    |
| Teacher  | teacher  | teacher  |
| Student  | student1 | student1 |

Or click "Create an Account" to register a new user.

## First-time Setup

When you log in for the first time as an administrator, you should:

1. **Update your profile information**
   - Click on your profile in the top-right corner
   - Select "Profile" from the dropdown menu
   - Update your personal information and password
   
2. **Add courses to the system**
   - Navigate to "Course Management"
   - Click "Add Course" to create new courses
   - Fill in course details (code, name, description, credits)
   
3. **Register students**
   - Navigate to "Student Management"
   - Click "Add Student" to register new students
   - Fill in student details and assign them to available courses

## Troubleshooting

If you encounter any issues during installation or startup:

### "java: command not found"

Ensure Java is properly installed and added to your system PATH:

```bash
# Check Java version
java -version
```

### Compilation errors

Make sure all required files are present in the source directory:

```bash
# List all Java files
ls -la src/*.java
```

### "Could not find or load main class App"

Ensure you're running the application from the project root directory and that compilation completed successfully.

## Next Steps

After successful installation, refer to the main [README.md](../README.md) for complete usage instructions and feature details.

For advanced configuration options, see the [Configuration Guide](CONFIGURATION.md).

## Need Help?

If you need assistance, please:

1. Check the [Troubleshooting](#troubleshooting) section above
2. Review the main [README.md](../README.md) file
3. Check for known issues in the repository's issue tracker
4. Contact the development team at support@example.com 