![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**Atensyon:** Ang mga kontributor ay laging welcome sa proyektong ito. Bago mag-bigay ng kontribusyon, basahin muna ng mabuti ang [Code of Conduct](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Ang mga kasalukuyang features

* Logging in
* Combat
* Friends list
* Teleportation
* Gacha system
* Co-op *partially* works
* Spawning monsters via console
* Inventory features (receiving items/characters, upgrading items/characters, etc)

## Quick setup guide

**Atensyon:** Para sa mga nangangailangan ng suporta, maaari kang sumali sa aming server [Discord](https://discord.gg/T5vZU6UyeG).

### Ang mga kailangan

* [Java SE - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher

  **Atensyon:** Kung gusto mo lang **paganahin** ang server, pwede naman ang **jre**.

* [MongoDB](https://www.mongodb.com/try/download/community) (recommended 4.0+)

* Proxy Daemon: [mitmproxy](https://mitmproxy.org/) (mitmdump, recommended), [Fiddler Classic](https://telerik-fiddler.s3.amazonaws.com/fiddler/FiddlerSetup.exe), etc.

### Running

**Atensyon:** Kung nag-update ka galing sa lumang version, paki-delete ang `config.json` para mag-regenerate ulit.

1. Get `grasscutter.jar`
   - I-download mo sa [releases](https://github.com/Grasscutters/Grasscutter/releases/latest) o sa [actions](https://github.com/Grasscutters/Grasscutter/actions/workflows/build.yml) o [bumuo ng iyong sariling server](#building).
2. Gawa ka ng `resources` folder sa directory kung nasaan ang grasscutter.jar at ilagay ang `BinOutput, ExcelBinOutput, Readables, Scripts, Subtitle, TextMap` folders sa loob ng resources folder *(Tingnan mo ang [wiki](https://github.com/Grasscutters/Grasscutter/wiki) para malaman mo kung saan mo makukuha yan)*
3. Paandarin ang Grasscutter gamit ang command na `java -jar grasscutter.jar`. **Siguraduhin mo na ang mongodb service ay naka-open din.**

### Connecting with the client

½. Gumawa ng account sa server console gamit ang [command](https://github.com/Grasscutters/Grasscutter/wiki/Commands#:~:text=account%20%3Ccreate|delete%3E%20%3Cusername%3E%20[UID]) na ito.

1. Redirect traffic: (pumili lang dapat ng isa)
    - mitmdump: `mitmdump -s proxy.py -k`

        - Trust CA certificate:

          - Ang CA certificate ay nasa `%USERPROFILE%\.mitmproxy`, i-double click ang `mitmproxy-ca-cert.cer` para ma-[install](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) o...

          - Via command line *(kailangan ng administration privileges)*

             ```shell
             certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
             ```

    - Fiddler Classic: Paadarin ang Fiddler Classic, turn on mo yung `Decrypt https traffic` sa (Tools -> Options -> HTTPS) at baguhin mo ang default port na nakalagay (Tools -> Options -> Connections) sa anumang numero maliban sa `8888`, i-load ang [script](https://github.com/Grasscutters/Grasscutter/wiki/Resources#fiddler-classic-jscript) na ito (copy and paste ang script sa `FiddlerScript` tab) at i-click ang `Save Script` button.

    - [Hosts file](https://github.com/Grasscutters/Grasscutter/wiki/Resources#hosts-file)

2. Set mo ung proxy sa `127.0.0.1:8080` or dun sa proxy port na iyong inilagay.

- Para sa mitmproxy: Pagkatapos mong i-setup ang network proxy at sa pag-install ng certificate, tingnan mo sa http://mitm.it/ kung ang traffic ay dumadaan sa mitmproxy.

**Pwede mo rin gamitin ang `start.cmd` to start the servers and proxy daemons automatically, pero kailagan mong i-setup ang JAVA_HOME environment at i-configure ang `start_config.cmd` file.**

### Building

Ang Grasscutter ay gumagamit ng Gradle para sa depedencies at building.

**Mga kailangan:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
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

Pag-katapos mong i-compile, check mo yung project directory at makikita mo yung jar na kinompile mo. Usually pag-dev version, ang dapat nakalagay diyan ay `grasscutter-<version>-dev.jar`.

### Ang mga server commands ay nasa [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands) na!

# Quick Troubleshooting

* Kung hindi nag-compile, paki-check ung JDK installation mo (JDK 17 at JDK's bin PATH variable).
* Hindi ako maka-connect, ayaw mag-login, 4206, etc... - Mostly ang proxy setup mo ang may kasalanan niyan, kung gamit mo ay Fiddler, paki-sigurado na naka-set ung port sa kahit ano except sa 8888.
* Ang pagkakasunud-sunod: MongoDB > Grasscutter > Proxy Daemon (mitmdump, fiddler, etc.) > Game
