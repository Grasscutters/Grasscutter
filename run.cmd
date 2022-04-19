@rem
@rem Copyright (C) 2002-2022 MlgmXyysd All Rights Reserved.
@rem

@if "%DEBUG%" == "" echo off
pushd %~dp0
title Grasscutter
call :LOG [INFO] Grasscutter
call :LOG [INFO] Initializing...

@rem This will not work if your java or mitmproxy is in a different location, plugin as necessary
@rem this just saves you from changing your PATH
set JAVA_PATH=
set MITMPROXY_PATH=
set PROXY_SCRIPT=proxy
@rem TODO: MongoDB integration
set SERVER_PATH=%~dp0

@rem detect java
setlocal enabledelayedexpansion
if "%JAVA_PATH%" == "" (
	if not "%JAVA_HOME%" == "" (
		set JAVA_PATH=%JAVA_HOME%
	)
)

if "%JAVA_PATH%" == "" (
	where java >nul 2>nul
	if "!ERRORLEVEL!" == "0" (
		for /f "usebackq tokens=*" %%F in (`where java`) do ( 
			set JAVA_PATH=%%F
		)
		set JAVA_PATH=!JAVA_PATH:~0,-12!
	)
)

@rem detect mitmproxy
if "%MITMPROXY_PATH%" == "" (
	where mitmdump >nul 2>nul
	if "!ERRORLEVEL!" == "0" (
		for /F "usebackq tokens=*" %%F in (`where mitmdump`) do ( 
			set MITMPROXY_PATH=%%F
		)
		set MITMPROXY_PATH=!MITMPROXY_PATH:~0,-12!
	)
)

@rem mitmproxy not found, server only
if not exist "%MITMPROXY_PATH%mitmdump.exe" (
	call :LOG [WARN] mitmproxy not found, server only mode.
	goto :SERVER
)
@rem proxy script not found, server only
if not exist "%PROXY_SCRIPT%.py" (
	if not exist "%PROXY_SCRIPT%.pyc" (
		call :LOG [WARN] Missing proxy script or compiled proxy script, server only mode.
		goto :SERVER
	) else set PROXY_SCRIPT=%PROXY_SCRIPT%.pyc
) else set PROXY_SCRIPT=%PROXY_SCRIPT%.py

:PROXY
@rem UAC Administrator privileges
>nul 2>&1 reg query "HKU\S-1-5-19" || (
	call :LOG [WARN] Currently running with non Administrator privileges, raising...
	echo set UAC = CreateObject^("Shell.Application"^) > "%temp%\UAC.vbs"
	echo UAC.ShellExecute "%~f0", "%1", "", "runas", 1 >> "%temp%\UAC.vbs"
	"%temp%\UAC.vbs"
	del /f /q "%temp%\UAC.vbs" >nul 2>nul
	exit /b
)

set PROXY=true
@rem Store original proxy settings
for /f "tokens=2*" %%a in ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable 2^>nul') do set "ORIG_PROXY_ENABLE=%%b"
for /f "tokens=2*" %%a in ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer 2^>nul') do set "ORIG_PROXY_SERVER=%%b"

call :LOG [INFO] Starting proxy daemon...
@rem TODO: External proxy when ORIG_PROXY_ENABLE == 0x1
start /min "" "%MITMPROXY_PATH%mitmdump.exe" -s %PROXY_SCRIPT% --ssl-insecure

@rem CA certificate for possible HTTPS scheme
call :LOG [INFO] Waiting for CA certificate generation...
set CA_CERT_FILE="%USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer"

set /A TIMEOUT_COUNT=0

:CERT_CA_CHECK
if not exist %CA_CERT_FILE% (
	timeout /T 1 >nul 2>nul
	set /A TIMEOUT_COUNT+=1
	goto CERT_CA_CHECK
)
:EXTRA_TIMEOUT
if %TIMEOUT_COUNT% LEQ 2 (
	timeout /T 1 >nul 2>nul
	set /A TIMEOUT_COUNT+=1
	goto EXTRA_TIMEOUT
)
call :LOG [INFO] Adding CA certificate to store...
certutil -addstore root %CA_CERT_FILE% >nul 2>nul

call :LOG [INFO] Setting up network proxy...
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d 1 /f >nul 2>nul
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer /d "127.0.0.1:8080" /f >nul 2>nul

:SERVER
if not exist "%JAVA_PATH%bin\java.exe" (
	call :LOG [ERROR] Java not found.
	goto :EXIT
)
if not exist "%SERVER_PATH%grasscutter.jar" (
	call :LOG [ERROR] Server jar not found.
	goto :EXIT
)
call :LOG [INFO] Starting server...
"%JAVA_PATH%bin\java.exe" -jar "%SERVER_PATH%grasscutter.jar"
call :LOG [INFO] Server stopped

:EXIT
if "%PROXY%" == "" (
	call :LOG [INFO] Proxy not started, no need to clean up.
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
setlocal disabledelayedexpansion

call :LOG [INFO] See you again :)
goto :EOF

:LOG
echo [%time:~0,8%] %*