@echo off
rem Lance le backend Kubinex avec les variables du fichier .env (local, non commite)
rem Usage: run-backend.bat [args java supplementaires]

if not exist ".env" (
    echo ERREUR: fichier .env introuvable. Creez-le depuis .env.example (VPS) ou ce dossier.
    exit /b 1
)

for /f "usebackq tokens=1,* delims==" %%a in (".env") do set "%%a=%%b"

echo Lancement du backend Kubinex sur http://localhost:8080 ...
java -jar target\kubinex-backend-1.0.0.jar %*
