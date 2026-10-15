@echo off
setlocal enabledelayedexpansion

:: Si se pasaron argumentos de línea de comandos (%1 no está vacío)
if not "%~1"=="" (
    for %%N in (%*) do (
        set /a "resultado=%%N * %%N"
        echo !resultado!
    )
) else (
    :: Si no hay argumentos, lee número a número desde la entrada estándar (stdin)
    for /f "tokens=*" %%N in ('more') do (
        set /a "resultado=%%N * %%N"
        echo !resultado!
    )
)