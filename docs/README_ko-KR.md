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

### 빠른 설치 (자동)

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 설치
- [MongoDB Community Server](https://www.mongodb.com/try/download/community) 설치
- 게임 버전 REL4.0.x 다운로드 (만약 4.0.x 클라이언트를 가지고 있지 않다면, 여기서 찾을 수 있습니다.):
[4.0.x 클라이언트 - GitHub](https://github.com/JRSKelvin/GenshinRepository/blob/main/Version%204.0.0.md)
[4.0.x 클라이언트 - 구글 드라이브브](https://www.123pan.com/s/HoqUVv-U7SBA.html)

- [최신 Cultivation](https://github.com/Grasscutters/Cultivation/releases/latest) 다운로드하세요. `.msi` 설치파일을 사용하면 됩니다.
- (관리자 권한으로) Cultivation을 실행한 후, 우측 상단에 위치한 다운로드 버튼을 클릭하세요.
- `올인원 다운로드`를 클릭하세요.
- 우측 상단에 위치한 톱니바퀴 버튼을 누르세요.
- 게임 설치 경로를 게임이 위치한 경로로 설정하세요.
- 사용자 지정 Java 경로 설정을 `C:\Program Files\Java\jdk-17\bin\java.exe`로 설정하세요.
- 다른 모든 설정은 기본값으로 두세요.

- 게임 시작 버튼 옆에 위치한 작은 버튼을 누르세요.
- 게임 시작 버튼을 누르세요.
- 원하는 사용자 이름으로 로그인하세요. 비밀번호는 무엇이든 가능합니다.

### 빌드하기

Grasscutter는 종속성 및 컴파일 처리를 위해 Gradle을 이용합니다.

**빌드하기 위해 필요한 것들 :**

- [Java SE 개발 키트 - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)
- [NodeJS](https://nodejs.org/en/download) (선택, 핸드북을 빌드하기 위해 필요함.)

##### 클론
```shell
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
```

##### 컴파일

**각주**: 핸드북 생성은 일부 시스템에서 실패할 수도 있습니다. 핸드북 생성을 비활성화하려면, `gradlew jar`명령에 `-PskipHandbook=1`명령줄 스위치를 추가하세요.


윈도우:

```shell
.\gradlew.bat # 환경 준비
.\gradlew jar
```

리눅스 (GNU):

```bash
chmod +x gradlew
./gradlew jar
```

##### 핸드북 컴파일 (수동동)

Gradle 사용:

```shell
./gradlew generateHandbook
```

NPM 사용:

```shell
cd src/handbook
npm install
npm run build
```

프로젝트 폴더의 최상단에서 jar 파일을 찾을 수 있습니다.

### 문제 해결
흔한 문제들의 해결방법과 도움을 요청하려면, [우리의 디스코드 서버](https://discord.gg/T5vZU6UyeG)에 참가하고 support 채널에 가보세요.