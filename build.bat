@echo off
echo Building Student Management System
echo ---------------------------------
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

echo Cleaning previous build...
if exist bin rmdir /s /q bin
mkdir bin

echo.
echo Compiling Java files...
javac -d bin src\*.java

REM Check if compilation was successful
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation failed. Please check the errors above.
    echo.
    pause
    exit /b
)

echo.
echo Creating manifest file...
echo Main-Class: App > manifest.txt

echo.
echo Creating JAR file...
cd bin
jar cvfm ../StudentManagementSystem.jar ../manifest.txt *.class
cd ..

REM Delete temporary files
del manifest.txt

echo.
echo Build completed successfully.
echo The application can be run using: java -jar StudentManagementSystem.jar
echo.
pause 