![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](docs/README_zh-CN.md) | [繁中](docs/README_zh-TW.md) | [FR](docs/README_fr-FR.md) | [ES](docs/README_es-ES.md) | [HE](docs/README_HE.md) | [RU](docs/README_ru-RU.md) | [PL](docs/README_pl-PL.md) | [ID](docs/README_id-ID.md) | [KR](docs/README_ko-KR.md) | [FIL/PH](docs/README_fil-PH.md) | [NL](docs/README_NL.md) | [JP](docs/README_ja-JP.md) | [IT](docs/README_it-IT.md) | [VI](docs/README_vi-VN.md)

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

**Note**: For support please join our [Discord](https://discord.gg/T5vZU6UyeG).

### Requirements

* [Java SE - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher

  **Note:** If you just want to **run it**, then **jre** only is fine.

* [MongoDB](https://www.mongodb.com/try/download/community) (recommended 4.0+)

* Proxy Daemon: [mitmproxy](https://mitmproxy.org/) (mitmdump, recommended), [Fiddler Classic](https://telerik-fiddler.s3.amazonaws.com/fiddler/FiddlerSetup.exe), etc.

### Running

**Note:** If you updated from an older version, delete `config.json` to regenerate it.

1. Get `grasscutter.jar`
   - Download from [releases](https://github.com/Grasscutters/Grasscutter/releases/latest) or [actions](https://github.com/Grasscutters/Grasscutter/actions/workflows/build.yml) or [build the server by yourself](#building).
2. Create a `resources` folder in the directory where grasscutter.jar is located and move your `BinOutput, ExcelBinOutput, Readables, Scripts, Subtitle, TextMap` folders there *(Check the [wiki](https://github.com/Grasscutters/Grasscutter/wiki) for more details how to get those.)*
3. Run Grasscutter with `java -jar grasscutter.jar`. **Make sure mongodb service is running as well.**

### Connecting with the client

½. Create an account in the server console using this [command](https://github.com/Grasscutters/Grasscutter/wiki/Commands#:~:text=account%20%3Ccreate|delete%3E%20%3Cusername%3E%20[UID]).

1. Redirect traffic: (choose one only)
    - mitmdump: `mitmdump -s proxy.py -k`

        - Trust CA certificate:

          - The CA certificate is usually stored in `%USERPROFILE%\.mitmproxy`, double click `mitmproxy-ca-cert.cer` to [install](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) or...

          - Via command line *(needs administration privileges)*

             ```shell
             certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
             ```

    - Fiddler Classic: Run Fiddler Classic, turn on `Decrypt HTTPS traffic` in (Tools -> Options -> HTTPS) and change the default port in (Tools -> Options -> Connections) to anything other than `8888`, load [this script](https://github.com/Grasscutters/Grasscutter/wiki/Resources#fiddler-classic-jscript) (copy and paste the script in the `FiddlerScript` tab) and click the `Save Script` button.

    - [Hosts file](https://github.com/Grasscutters/Grasscutter/wiki/Resources#hosts-file)

2. Set network proxy to `127.0.0.1:8080` or the proxy port you specified.

- For mitmproxy: After setting up the network proxy and installing the certificate, check http://mitm.it/ if traffic is passing through mitmproxy.

**You can also use `start.cmd` to start servers and proxy daemons automatically, but you have to set up `JAVA_HOME` environment and configure the `start_config.cmd` file.**

### Building

Grasscutter uses Gradle to handle dependencies & building.

**Requirements:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Setting up environments
.\gradlew jar # Compile
```

##### GNU/Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compile
```

You can find the output jar in the root of the project folder.

### Troubleshooting 

For a list of common issues and solutions and to ask for help, please join [our Discord server](https://discord.gg/T5vZU6UyeG) and go to the support channel.
