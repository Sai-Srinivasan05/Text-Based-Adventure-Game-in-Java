@echo off
echo =========================================
echo    Text-Based Adventure Game
echo =========================================
echo.
echo Compiling Java files...
cd src
javac *.java
if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    echo Please check your Java installation and file syntax.
    pause
    exit /b 1
)

echo.
echo Compilation successful! Starting game...
echo.
java Game
echo.
echo Thanks for playing!
pause