@echo off
echo =========================================
echo   Text-Based Adventure Game - GUI Version
echo =========================================
echo.
echo Starting the graphical adventure game...
echo.
cd src
java AdventureGameGUI
if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Failed to start the GUI game!
    echo Make sure Java is properly installed.
    pause
    exit /b 1
)
echo.
echo Game closed. Thanks for playing!
pause