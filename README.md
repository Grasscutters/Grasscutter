![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

EN | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md)

**Attention:** We always welcome contributors to the project. Before adding your contribution, please carefully read our [Code of Conduct](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Current features

* Logging in
* Combat
* Friends list
* Teleportation
* Gacha system
* Co-op *partially* works
* Spawning monsters via console
* Inventory features (receiving items/characters, upgrading items/characters, etc)

## Quick setup guide

**Note:** For support please join our [Discord](https://discord.gg/T5vZU6UyeG).

### Requirements

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Note:** If you just want to **run it**, then **jre** only is fine.

* [MongoDB](https://www.mongodb.com/try/download/community) (recommended 4.0+)

* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Running

**Note:** If you updated from an older version, delete `config.json` to regenerate it.

1. Get `grasscutter.jar`
   - Download from [actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Build by yourself](#Building)
2. Create a `resources` folder in the directory where grasscutter.jar is located and move your `BinOutput` and `ExcelBinOutput` folders there *(Check the [wiki](https://github.com/Grasscutters/Grasscutter/wiki) for more details how to get those.)*
3. Run Grasscutter with `java -jar grasscutter.jar`. **Make sure mongodb service is running as well.**

### Connecting with the client

½. Create an account using [server console command](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Redirect traffic: (choose one)
    - mitmdump: `mitmdump -s proxy.py -k`
    
      Trust CA certificate:
    
      ​	**Note:**The CA certificate is usually stored in `%USERPROFILE%\ .mitmproxy`, or you can download it from `http://mitm.it`
    
      ​	Double click for [install](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) or ...
    
      - Via command line
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: Run Fiddler Classic, turn on `Decrypt https traffic` in setting and change the default port there (Tools -> Options -> Connections) to anything other than `8888`, and load [this script](https://github.lunatic.moe/fiddlerscript).
      
    - [Hosts file](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)
    
2. Set network proxy to `127.0.0.1:8080` or the proxy port you specified.

**you can also use `start.cmd` to start servers and proxy daemons automatically, but you have to set up JAVA_HOME enviroment**

### Building

Grasscutter uses Gradle to handle dependencies & building.

**Requirements:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Setting up environments
.\gradlew jar # Compile
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compile
```

You can find the output jar in the root of the project folder.

### Commands have moved to the [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Quick Troubleshooting

* If compiling wasn't successful, please check your JDK installation (JDK 17 and validated JDK's bin PATH variable)
* My client doesn't connect, doesn't login, 4206, etc... - Mostly your proxy daemon setup is *the issue*, if using
  Fiddler make sure it running on another port except 8888
* Startup sequence: MongoDB > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Game
