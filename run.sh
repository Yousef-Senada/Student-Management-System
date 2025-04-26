#!/bin/bash

echo "Student Management System"
echo "-------------------------"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed or not in your PATH."
    echo "Please install Java Development Kit (JDK) 8 or higher."
    echo
    read -p "Press Enter to exit..."
    exit 1
fi

echo "Compiling application..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
javac -d bin src/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo
    echo "Compilation failed. Please check the errors above."
    echo
    read -p "Press Enter to exit..."
    exit 1
fi

echo "Compilation successful."
echo
echo "Starting application..."
echo

# Run the application
java -cp bin App

echo
echo "Application closed."
read -p "Press Enter to exit..." 