@rem
@rem Copyright (C) 2002-2022 MlgmXyysd All Rights Reserved.
@rem

@if "%DEBUG%" == "" echo off
pushd %~dp0
set CUR_PATH=%~dp0
title Grasscutter
call :LOG [INFO] Welcome to Grasscutter
call :LOG [INFO] To proper exit this console, use [Ctrl + C] and enter N not Y.
call :LOG [INFO]
call :LOG [INFO] Initializing...

set CONFIG=start_config

set JAVA_PATH=DO_NOT_CHECK_PATH
set MITMDUMP_PATH=DO_NOT_CHECK_PATH
set MONGODB_PATH=DO_NOT_CHECK_PATH

set SERVER_JAR_PATH=%CUR_PATH%
set DATABASE_STORAGE_PATH=%CUR_PATH%resources\Database

set SERVER_JAR_NAME=grasscutter.jar
set PROXY_SCRIPT_NAME=proxy

if exist "%CUR_PATH%%CONFIG%.cmd" (
	call "%CUR_PATH%%CONFIG%.cmd" >nul 2>nul
)

if not "%JAVA_PATH%" == "DO_NOT_CHECK_PATH" (
	if "%JAVA_PATH%" == "\bin\" (
		call :LOG [ERROR] JAVA_HOME not found, please setup your windows enviroment for installed java.
		goto :EXIT
	)
	if not exist "%JAVA_PATH%java.exe" (
		call :LOG [ERROR] Java not found.
		goto :EXIT
	)
) else set JAVA_PATH=
if not exist "%SERVER_PATH%%SERVER_JAR_NAME%" (
	call :LOG [ERROR] Server jar not found.
	goto :EXIT
)

@rem mitmproxy not found, server only
if not "%MITMDUMP_PATH%" == "DO_NOT_CHECK_PATH" (
	if not exist "%MITMDUMP_PATH%mitmdump.exe" (
		call :LOG [WARN] mitmdump not found, server only mode.
		goto :SERVER
	)
) else set MITMDUMP_PATH=
@rem proxy script not found, server only
if not exist "%PROXY_SCRIPT_NAME%.py" (
	if not exist "%PROXY_SCRIPT_NAME%.pyc" (
		call :LOG [WARN] Missing proxy script or compiled proxy script, server only mode.
		goto :SERVER
	) else set PROXY_SCRIPT_NAME=%PROXY_SCRIPT_NAME%.pyc
) else set PROXY_SCRIPT_NAME=%PROXY_SCRIPT_NAME%.py

:PROXY
@rem UAC Administrator privileges
>nul 2>&1 reg query "HKU\S-1-5-19" || (
	call :LOG [WARN] Currently running with non Administrator privileges, raising...
	echo set UAC = CreateObject^("Shell.Application"^) > "%temp%\UAC.vbs"
	echo UAC.ShellExecute "%~f0","%1","","runas",1 >> "%temp%\UAC.vbs"
	"%temp%\UAC.vbs"
	del /f /q "%temp%\UAC.vbs" >nul 2>nul
	exit /b
)

call :LOG [INFO] Starting proxy daemon...

set PROXY=true

@rem Store original proxy settings
for /f "tokens=2*" %%a in ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable 2^>nul') do set "ORIG_PROXY_ENABLE=%%b"
for /f "tokens=2*" %%a in ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer 2^>nul') do set "ORIG_PROXY_SERVER=%%b"

@rem TODO: External proxy when ORIG_PROXY_ENABLE == 0x1
echo set ws = createobject("wscript.shell") > "%temp%\proxy.vbs"

if not "%MITMDUMP_PATH%" == "" (
	echo ws.currentdirectory = "%MITMDUMP_PATH%" >> "%temp%\proxy.vbs"
)
echo ws.run "cmd /c mitmdump.exe -s "^&chr(34)^&"%CUR_PATH%%PROXY_SCRIPT_NAME%"^&chr(34)^&" -k --allow-hosts "^&chr(34)^&".*\.yuanshen\.com|.*\.mihoyo\.com|.*\.hoyoverse\.com"^&chr(34),0 >> "%temp%\proxy.vbs"
"%temp%\proxy.vbs"
del /f /q "%temp%\proxy.vbs" >nul 2>nul

@rem CA certificate for HTTPS scheme
call :LOG [INFO] Waiting for CA certificate generation...
set CA_CERT_FILE="%USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer"

set /a TIMEOUT_COUNT=0

:CERT_CA_CHECK
if not exist %CA_CERT_FILE% (
	timeout /t 1 >nul 2>nul
	set /a TIMEOUT_COUNT+=1
	goto CERT_CA_CHECK
)
:EXTRA_TIMEOUT
if %TIMEOUT_COUNT% LEQ 2 (
	timeout /t 1 >nul 2>nul
	set /a TIMEOUT_COUNT+=1
	goto EXTRA_TIMEOUT
)
call :LOG [INFO] Adding CA certificate to store...
certutil -addstore root %CA_CERT_FILE% >nul 2>nul

call :LOG [INFO] Setting up network proxy...
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d 1 /f >nul 2>nul
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer /d "127.0.0.1:8080" /f >nul 2>nul

:SERVER
if not "%MONGODB_PATH%" == "DO_NOT_CHECK_PATH" (
	if not exist "%MONGODB_PATH%mongod.exe" (
		call :LOG [WARN] MongoDB daemon not found, server only mode.
		goto :GAME
	)
) else set MONGODB_PATH=
call :LOG [INFO] Starting MongoDB daemon...
set DATABASE=true

mkdir "%DATABASE_STORAGE_PATH%" >nul 2>nul

echo set ws = createobject("wscript.shell") > "%temp%\db.vbs"
if not "%MONGODB_PATH%" == "" (
echo ws.currentdirectory = "%MONGODB_PATH%" >> "%temp%\db.vbs"
)
echo ws.run "cmd /c mongod.exe --dbpath "^&chr(34)^&"%DATABASE_STORAGE_PATH%"^&chr(34)^&"",0 >> "%temp%\db.vbs"
"%temp%\db.vbs"
del /f /q "%temp%\db.vbs" >nul 2>nul

:GAME
call :LOG [INFO] Starting server...
"%JAVA_PATH%java.exe" -jar "%SERVER_PATH%%SERVER_JAR_NAME%"
call :LOG [INFO] Server stopped

:EXIT
if "%DATABASE%" == "" (
	call :LOG [INFO] MongoDB daemon not started, no need to clean up.
) else (
	call :LOG [INFO] Shutting down MongoDB daemon...
	taskkill /t /f /im mongod.exe >nul 2>nul
)
if "%PROXY%" == "" (
	call :LOG [INFO] Proxy daemon not started, no need to clean up.
) else (
	call :LOG [INFO] Restoring network settings...

	reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d "%ORIG_PROXY_ENABLE%" /f >nul 2>nul
	reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer /d "%ORIG_PROXY_SERVER%" /f >nul 2>nul

	call :LOG [INFO] Shutting down proxy daemon...
	taskkill /t /f /im mitmdump.exe >nul 2>nul

	call :LOG [INFO] Removing CA certificate...
	for /F "tokens=2" %%s in ('certutil -dump %CA_CERT_FILE% ^| findstr ^"^sha1^"') do (
		set SERIAL=%%s
	)

	certutil -delstore root %SERIAL% >nul 2>nul
)

call :LOG [INFO] See you again :)
goto :EOF

:LOG
echo [%time:~0,8%] %*
