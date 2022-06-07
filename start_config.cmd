@rem
@rem Copyright (C) 2002-2022 MlgmXyysd All Rights Reserved.
@rem

@echo off
pushd %~dp0
set CUR_PATH=%~dp0

@rem This will not work if your java or mitmproxy is in a different location, plugin as necessary
@rem this just saves you from changing your PATH

@rem Executable Path
@rem Note: Fill DO_NOT_CHECK_PATH if you need to run it from PATH
@rem without detecting whether the executable file exists
set JAVA_PATH=%JAVA_HOME%\bin\
set MITMDUMP_PATH=%CUR_PATH%
set MONGODB_PATH=%CUR_PATH%

@rem Utility Path
set SERVER_JAR_PATH=%CUR_PATH%
set DATABASE_STORAGE_PATH=%CUR_PATH%resources\Database

@rem Utility Name
set SERVER_JAR_NAME=grasscutter.jar
set PROXY_SCRIPT_NAME=proxy