@echo off
setlocal enabledelayedexpansion
set "suma=0"

:: Si se pasaron argumentos de línea de comandos (%1 no está vacío)
if not "%~1"=="" (
    for %%N in (%*) do (
        set /a "suma+=%%N"
    )
) else (
    :: Si no hay argumentos, lee desde la entrada estándar (stdin / pipe)
    for /f "tokens=*" %%N in ('more') do (
        set /a "suma+=%%N"
    )
)

echo %suma%