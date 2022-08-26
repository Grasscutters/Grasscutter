![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

EN | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | FIL/PH | [NL](README_NL.md) | [JP](README_jp-JP.md)

**Atensyon:** Ang mga kontributor ay laging welcome sa proyektong ito. Bago mag-bigay ng kontribusyon, basahin muna ng mabuti ang [Code of Conduct](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md). 

<b>(Basahin ha, hindi titingin lang)</b>

## Ang mga current features

* Login system
* Combat
* Friends list
* Teleportation
* Gacha system
* Co-op *partially* works
* Spawning monsters via console
* Inventory features (receiving items/characters, upgrading items/characters, etc)

## Quick setup guide

**Atensyon:** Kung di mo talaga kaya o hindi mo maintindihan ang wiki, maaari kang sumali sa aming server [Discord](https://discord.gg/T5vZU6UyeG).

### Ang mga kailangan

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Atensyon:** Kung gusto mo lang **paganahin** ang server, then **jre** is pwede naman.

* [MongoDB](https://www.mongodb.com/try/download/community) (recommended 4.0+)

* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Running

**Atensyon:** Kung nag-update ka galing sa old version, paki-delete ang `config.json` para mag-regenerate ulit.

1. Get `grasscutter.jar`
   - Download ka from [actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Build mo ung jar by yourself](#Building)
2. Gawa ka ng `resources` folder sa directory kung nasaan ang grasscutter.jar at ilagay ang `BinOutput` at `ExcelBinOutput` sa loob ng resources folder *(Check mo ang [wiki](https://github.com/Grasscutters/Grasscutter/wiki) para malaman mo san mo makukuha yan)*
3. Paandarin ang Grasscutter gamit ang command na `java -jar grasscutter.jar`. **Make sure na gumagana ang mongodb (Google mo nalang kung pano mo malalaman)** 

### Connecting with the client

½. Create ka ng account gamit ang [server console command](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Redirect traffic: (choose one)
    - mitmdump: `mitmdump -s proxy.py -k`

      Trust CA certificate:

      ​	**Note:** Usually ang CA certificate ay nakalagay sa `%USERPROFILE%\ .mitmproxy`, o pwede mo naman i-download from `http://mitm.it`

      ​	Double click para ma-[install](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) or ...

      - Gamit ang command line (cmd.exe)

        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```

    - Fiddler Classic: Paadarin ang Fiddler Classic, tsaka turn on mo yung `Decrypt https traffic` sa settings at baguhin mo yung default port na nakalagay (Tools -> Options -> Connections) to anything other than `8888`, at saka mo i-load [itong script](https://github.lunatic.moe/fiddlerscript).

    - [Hosts file](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)

2. Set mo ung proxy sa `127.0.0.1:8080` or dun sa proxy port na iyong inilagay.

**pwede mo rin gamitin ang `start.cmd` to start the servers and proxy daemons automatically, pero kailagan mong i-setup ang JAVA_HOME enviroment**

### Building

Ang Grasscutter ay gumagamit ng gradle for depedencies at building.

**Mga kailangan:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Setting up environments
.\gradlew jar # Compile jar
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compile jar
```

Pag-katapos mong i-compile, check mo yung [project](https://github.com/grasscutters/grasscutter) directory at saka makikita mo ung jar na kinompile mo. Usually pag-dev version, ang dapat nakalagay jan ay `grasscutter-<version>-dev.jar`. Bulag ka pag-hindi mo pa yan nakita.

### Atensyon: ang mga server commands ay nasa [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Quick Troubleshooting

* Kung hindi nag-compile, paki-check ung JDK installation mo (JDK 17 at JDK's bin PATH variable). Pag-hindi mo pa rin na-compile o hindi mo ma-gets, isa lang masasabi ko sayo, may skill issue+reading issue ka. 
* Hindi ako maka-connect, ayaw mag-login, 4206, etc... - `Usually proxy may kasalanan nyan`, ito ung pinaka-malalang skill issue na pwede mong makuha, sa lahat ng problems sa gc. Kung ayaw mo nyan, basahin mo ito. 

Kung <b>Fiddler user</b> ka, paki-sigurado na naka-set ung port sa kahit ano except sa `8888`. (8888 port is for hoyoverse spider logs, in case na hindi mo alam)
* Startup sequence: MongoDB > Grasscutter > Proxy daemon o Proxy service (mitmdump, fiddler, etc.) 

<b> KUNG HINDI MO TALAGA MAINTINDIHAN, LUMAYAS KA NA DITO......... 
PUTANG INA MO, TAGLISH NA NGA YAN. TAS HINDI MO PA MA-GETS LMAO</b>