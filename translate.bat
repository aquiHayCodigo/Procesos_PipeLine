@echo off
set "LANG_ARG=%~1"

if "%LANG_ARG%"=="-en" set "LANG=en"
if "%LANG_ARG%"=="-fr" set "LANG=fr"
if "%LANG_ARG%"=="-it" set "LANG=it"
if "%LANG_ARG%"=="en" set "LANG=en"
if "%LANG_ARG%"=="fr" set "LANG=fr"
if "%LANG_ARG%"=="it" set "LANG=it"

if "%LANG%"=="" exit /b 1

powershell -NoProfile -ExecutionPolicy Bypass -Command "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; $raw = [Console]::In.ReadToEnd(); if (-not [string]::IsNullOrWhiteSpace($raw)) { $enc = [System.Uri]::EscapeDataString($raw.Trim()); $res = Invoke-RestMethod -Uri ('https://api.mymemory.translated.net/get?q=' + $enc + '&langpair=es|%LANG%'); Write-Output $res.responseData.translatedText }"