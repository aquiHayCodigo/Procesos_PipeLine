@echo off

:: 1. Comprobar que se han pasado los 2 argumentos
if "%~2"=="" (
    echo Error. Uso correcto: %~nx0 ^<archivo_de_texto^> ^<palabra^>
    exit /b 1
)

set "ARCHIVO=%~1"
set "PALABRA=%~2"

:: 2. Comprobar si el archivo de texto realmente existe
if not exist "%ARCHIVO%" (
    echo Error: El archivo "%ARCHIVO%" no existe.
    exit /b 1
)

:: 3. Contar las apariciones exactas usando una llamada rápida a PowerShell
:: \b indica "límite de palabra" para que "sol" no coincida dentro de "soldado".
:: 'IgnoreCase' hace que no distinga entre mayúsculas y minúsculas.
for /f "delims=" %%C in ('powershell -NoProfile -Command "([regex]::Matches((Get-Content '%ARCHIVO%' -Raw), '\b%PALABRA%\b', 'IgnoreCase')).Count"') do set CANTIDAD=%%C

:: 4. Mostrar el resultado
echo %CANTIDAD%