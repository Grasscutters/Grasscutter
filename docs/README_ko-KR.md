![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**주의 :** 우리는 항상 프로젝트에 기여하는 사람들을 환영합니다. 기여를 하기 전, [행동 지침](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md)을 주의 깊게 읽어주세요.

## 현재 기능들

* 로그인
* 전투
* 친구 목록
* 워프 (TP)
* 기원
* 다인 모드 (부분적으로 작동)
* 콘솔을 통한 몬스터 스폰
* 인벤토리 기능 (캐릭터, 아이템 수령 및 캐릭터, 아이템 업그레이드 등)

## 설치 가이드

**각주 :** 도움이 필요할 경우 [Discord](https://discord.gg/T5vZU6UyeG)에 가입하세요.

### 설치에 필요한 것들

* Java SE - 17 ([링크](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **각주 :** **실행**만을 원한다면, **jre**만 있어도 괜찮습니다.

* [MongoDB](https://www.mongodb.com/try/download/community) (4.0 이상의 버전 추천)

* 프록시 데몬 : mitmproxy (mitmdump 추천), Fiddler Classic 등.

### 실행

**각주 :** 구버전에서 업데이트 했을 경우, `config.json` 파일을 재생성하기 위해 파일을 삭제하세요.

1. `grasscutter.jar` 얻기
   - [Actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297) 탭에서 다운로드
   - [직접 빌드하기](#빌드하기)
2. grasscutter.jar 파일이 위치한 폴더에 `resources` 폴더를 생성하고, `BinOutput` 과 `ExcelBinOutput` 폴더를 생성한 폴더 내로 옮기세요. *(이 파일들을 얻는 더 자세한 방법에 대해서는 [위키](https://github.com/Grasscutters/Grasscutter/wiki)를 참조하세요.)*
3. Grasscutter를 `java -jar grasscutter.jar` 명령어로 실행합니다. **MongoDB 서비스가 정상적으로 실행되고 있는지 확인하세요.**

### 클라이언트와의 연결

½. [서버 콘솔 명령어](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting)를 이용해서 계정을 생성합니다.

1. 리다이렉트 트래픽 : (1가지 선택)
    - mitmdump: `mitmdump -s proxy.py -k`

      신뢰하는 인증 기관 인증서 (CA Cert) :

      ​	**각주 :** CA 인증서는 보통 `%USERPROFILE%\ .mitmproxy` 경로에 저장되며,  `http://mitm.it`에서 다운로드 받을 수도 있습니다.

      ​	더블 클릭하여 [설치](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) 또는 ...

      - 명령어를 통해서

        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```

    - Fiddler Classic : Fiddler Classic을 실행한 후, Setting에서 `Decrypt https traffic` 옵션을 켜고, Tools -> Options -> Connections에 있는 기본 포트를 `8888`을 제외한 다른 포트로 지정합니다. 그리고 [이 스크립트](https://github.lunatic.moe/fiddlerscript)를 불러옵니다.

    - [호스트 파일](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)

2. 네트워크 프록시를 `127.0.0.1:8080` 로 설정하거나 지정한 프록시 포트로 설정합니다.

**또한 `start.cmd`를 실행함으로써, 서버와 프록시 데몬을 자동으로 실행되게 할 수 있습니다. 이를 이용하기 위해서는 JAVA_HOME 환경 변수를 등록해야 합니다.**

### 빌드하기

Grasscutter는 종속성 및 컴파일 처리를 위해 Gradle을 이용합니다.

**빌드하기 위해 필요한 것들 :**

- [Java SE 개발 키트 - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### 윈도우 (온라인)

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # 개발 환경 설정
.\gradlew jar # 컴파일
```

##### 윈도우 (로컬)

```shell
cd <로컬 주소>/Grasscutter
.\gradlew.bat # 개발 환경 설정
.\gradlew jar # 컴파일
```

##### 리눅스

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # 컴파일
```

프로젝트 폴더의 최상단에서 jar 파일을 찾을 수 있습니다.

### 명령어들은 [위키](https://github.com/Grasscutters/Grasscutter/wiki/Commands)에서 확인할 수 있습니다.

# 빠른 문제 해결

* 만약 컴파일링이 정상적으로 완료되지 않을 경우, JDK 설치를 확인하세요. (JDK 버전 17 및 JDK의 bin 경로 변수 등록을 확인)
* 클라이언트가 연결되지 않거나, 로그인이 안 되거나, 4206 오류가 뜨는 등의 경우 - 대부분 프록시 데몬의 설치에 문제가 있을 것입니다. Fiddler를 사용하고 있다면, 8888을 제외한 다른 포트에서 구동되고 있는지 확인하세요.
* 구동 순서 : MongoDB > Grasscutter > 프록시 데몬 (mitmdump, fiddler 등) > 게임
