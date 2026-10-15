@echo off
setlocal enabledelayedexpansion

REM 1. Guardar la entrada estándar (stdin) en un archivo temporal
set "TEMP_FILE=%TEMP%\check_input_%RANDOM%.txt"
more > "%TEMP_FILE%"

REM 2. Ejecutar PowerShell para analizar y corregir el texto con la API de LanguageTool (Español)
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; " ^
  "$text = Get-Content -Path '%TEMP_FILE%' -Raw -Encoding UTF8; " ^
  "if ([string]::IsNullOrWhiteSpace($text)) { exit 0 }; " ^
  "$body = @{ text = $text; language = 'es' }; " ^
  "try { " ^
  "   $response = Invoke-RestMethod -Uri 'https://api.languagetool.org/v2/check' -Method Post -Body $body; " ^
  "   $matches = $response.matches | Sort-Object -Property offset -Descending; " ^
  "   foreach ($m in $matches) { " ^
  "       if ($m.replacements.Count -gt 0) { " ^
  "           $sug = $m.replacements[0].value; " ^
  "           $offset = $m.offset; " ^
  "           $length = $m.length; " ^
  "           $text = $text.Remove($offset, $length).Insert($offset, $sug); " ^
  "       } " ^
  "   }; " ^
  "   Write-Output $text.Trim(); " ^
  "} catch { " ^
  "   Write-Output $text.Trim(); " ^
  "}"

REM 3. Limpiar archivo temporal
del "%TEMP_FILE%" 2>nul