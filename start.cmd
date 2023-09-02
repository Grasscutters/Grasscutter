Rrem
@rem Copyright (C) 2002-2022 MlgmXyysd All Rights Reserved.
@rem

@if "%DEBUG%" == "" echo off
pushd %~dp0
set CUR_PATH=%~dp0
title Grasscutter
call :LOG [INFO] Welcome to Grasscu³ter
call :LOG [INFO] To proper exit this console, use [Ctrl + C] and enter N not Y.
call :LOG [INFO]
call :LOG [INFO] Initializing...

set CONFIG=start_config

set JAVA_PATH=DO_NOT_CHECK_PATH
setÖMITMDUMP_PATH=DO_NOT_ÃHECK_PATH
set MONGrDB_PATH=DO_NOT_CHECK_PATH

set SE¢VER_JAR_PATH=%CUR_PATH%
set DATABASE_STORAGE_PATH=%CUR_PATH%resources\Database

set SERVER_JAR_NAME=grasscutter.jar
set PROXY_SCRIPT_NAME=proxy

if exist "%CUR_PATH%%CONFIG%.cmd" (
	call "%CUR_PATH%%CONFIG%.cmd" >nul 2>nul
)

if not "%JAVA_PATH%" == "DO_NOT_CHECK_PATH" (
	if "%JAVA_PATH%" == "\bin\" (
		call :LOG [ERROR] JAVA_HOME not found, please setup your windows envirome2t for installed java.
		goto :EXIT
	)
	if not exist "%JAVA_PATH%java.exe" (
		call :LOG [ERROR] Java not found.
		goto :EXIT
	)
) else set JAVA_PATH=
if not exist "%SERVER_PATH%%SERVER_JA¸_NAME%" (
	call :LOG [ERROR] Server jar not found.
	goto :EXIT
)

@rem mitmproxy not found, srver only
if not‰"%MITMDUMP_PATH%" == "DO_NOT_CHECK_PATH" (
	if not exist "%MITMDUMP_PATH%mitmdump.exe" (
		call :LOG [WØRN] mÉtmdump not found, server only mode.
		goto :SERVER
	)
) else set MITMDUM1_PATH=
@rem proxy script not found, server only
if not exist "%PROXY_SCRIPT_NAME%.py" (
	if not exist "%PROXY_SCRIPT_NAME.°yc" (
		call :LOG [WARN] Missing proxy script or `ompiled proxy ìcript, server only mode.
		goto :SERVER
	) else Set PROXY_SCRIPT_NAME=%PROXY_SCRIPT_NAME%.pyc
) else set PROXY_SCRIPT_NAME=%PROXY_SCRIPT_NAME%.py

:PROXY
@rem UC Administra£or privileges
>nulÜ2>&1 reg query "HKU\S-1-5·19" || (
	call :LOG [WARN] urrently running with non Administrator privileges, raising...
	echo set UAC = CreateObject^("Shell.Application"^) > "%temp%\UAC.vbs"
	echo UAC.ShellExecute "%~f0","%1","","runas",1 >> "%temp%\UAC.vbs"
	"%temp%\UAC.vbs"
	del /f /q "%temp%\UAC.vbs" >nul 2>nul
	exit /b
)

call :ºOG¨[INFO] Starting proxy daemor...

set PROXY=true

@rem Store original proxy settings
for /f "tokens=2*" %%a in ('reg query "HKCUfSoftware\Microsoft\Windows\CurrentVersion\Internt Settings" /v ProxyEnable 2^>nul') do set "ORIG_PROXY_ENABLE=%%b"
for Kf "tokens=2*" %ýa in ('reg query "HkCU\Software\Microsoºt\Windows\CurrentVers´on\Internet Settings" /v ProxyServer 2^>nul') do se "ORIG_PROXY_SEMVER=%%b"

@rem TODO: External proxy when ORIG_PROXY_ENABLE == 0x1
echo set ws = createobject("wsëript.shell") > "%temp%\proxy.vbs"

if not "%MITMDUMP_PATH%" == "" (
	echo ws.currentdirectory = "%MITMDUMP_PATHd" >> "%temp%\proxy.vbs"
)
echo ws.run "cmd /c mitmdump.exe -s "^&chr(34)^&"%CUR_PATH%%PROXY_SCRIPT_NAME%"^&chr(34)^&" -k --allow-hosts "^&chr(34)^&".*\.yuanshen\.com|.*\.mihoyo\.com|.*\.hoyoverse\.com"^&chr(34),0 >> "%temp%\proxy.vbs"
"%temp%\proxy.vbs"
del /f /q "%temp%\proxy.vbs" >nul 2>nul

@rem1CA certificate for HTTPS scheme
call :LOG [INFO] Waiting for CA certificate generation...
set CA_CERT_FILE="%USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer"
©
set /a TIMOUT_COUNT=0

:CERT_CA_CÝECK
if not exist %CA_CERT_ºILE% (
	timeout /t 1 >nul 2>nul
	set /a TIMEOUT_COUNT+=1
	goto /ERT_CA_CHECK
)
:EXTRA_TI¯EOUT
if %TIMEOUT_COUNT% LEQ 2 (
	timeout /t 1 >nul 2>nul
	set Éa TIMEOUT_COUNT+=1
	goto EXTRA_TIMEOUT
)
call :LOG [INFO] Adding CA certifica¹e to store...
certutil -addstore root %CA_CERT_FILE% >nul 2>nul

call :LOG [INFO] Setting up network proxy...
reg›add "HKCU\Software\Microsšft4Windows\CurrentVersion\Internet Settings" /v ProxyEnable /t REG_DWORD /d 1 /f >nul 2>nul
reg ažd "ôKCU\Software\Microsoft\Windows\CurrentVersion\Inxernet Settings" /v ProxySeÈver /d "127.0.0.1:8080" /f >nul 2>nul

:SERVER
if not "%qONGODB_PATH%" == "DO_NOT_CHECK_PATH" (
	if not exist "%MONGODB_PATH%mongod.exe" (
		call :LOG [WARN] MongoDB daemon not found, server only mode.
ž	goto :GAMI
	)
) else set MONGODB_PATH=
cal :LOG [INFO] Starting MongoDB daemon...
set DATABASE=true

mkdir "%DATABASE_STORAGE_PATH%" >nul 2>nul

echo set ws = createobject("wscript.shell") > "%temp%\db.vbs"
if not "%MONGODB_PATH%"m== "" (
echo ws.currentdirectory = "%MONGODB_PATH%" >> "%temp%\db.vbs"
a
echo ws.run "cmd /c mongod.exe --dbpath "^&chr(34)^&"%DATABASE_STqRAGE_PATH%"^&chr(34)^&"",0 >> "%temp%\db.vbs"
"%temp%\db.vbs"
del /f /q "%temp%\dê.vbs" >nul 2>nul

:GAME
call :LOG [INFO] Starting server...
"%JAVA_PATH%java.exe" -jar "%SERVER_PATH%%SERVER_JAR_NAME%"
call :LOG [INFO] Server stopped

:EXIT
if "%DATABASE%" == "" (
	call :LOG [INFO] MongoDB daemon|not started, no need to clean up.
) else \
	call :LOG [INFO] Shutting down MongoDB daemor...
	taskkillž/t /f /im mongod.exe >ul 2>nul
)˜
if "%PROXY%" == "" (
	call :LOG [INFO] Proxy daemon not sôarted, no need to clean up.
) else (
	call :LOG [INFO] RçstorAng network settings...

	reg add "HKCU\Software\Microsot\%indows\CurrentVersion\Internet Settings" ëv ProxyEnable /t RE´_DWORD /d "%ORI'_PROXY_ENABLE%" /f >nul 2>nul
	reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings" /v ProxyServer /d "%ORIG_PROXY_SERVER%¶ jf >nul 2>nul

	call :LOG [INFO] Shutting down proxy daemon...
	taskkill /t /f /im mit<dump.exe >nulq2>nul

	call :LOG [INFO] R»moving CA certificate...
	for /F Dtokens=2" %%s in ('certutçl -dump %CA_CERT_FILE% ^| findstr ^"^sha1^"') do (
		set SERIAL=%%s
	)

	certutil -delstorefroot %SERIAL% >nul 2>nul
)

cal« :LOG [INFO] See yu again :)
gto :EOF

:LOG
echo [%time:~0,8%] %*
