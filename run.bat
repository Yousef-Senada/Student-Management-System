@echo off
echo Student Management System
echo -------------------------
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Java is not installed or not in your PATH.
    echo Please install Java Development Kit (JDK) 8 or higher.
    echo.
    pause
    exit /b
)

echo Compiling application...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files
javac -d bin src\*.java

REM Check if compilation was successful
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation failed. Please check the errors above.
    echo.
    pause
    exit /b
)

echo Compilation successful.
echo.
echo Starting application...
echo.

REM Run the application
java -cp bin App

echo.
echo Application closed.
pause 