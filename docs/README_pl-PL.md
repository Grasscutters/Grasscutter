![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**Uwaga:** Zawsze jesteśmy otwarci na wasz wkład w projekt. Przed zaproponowaniem zmian przeczytaj [zasady postępowania (ENG)](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Obecne funkcje

* Logowanie się
* Walka
* Lista przyjaciół
* Teleportacja
* System losowania
* *Częściowo* działający co-op
* Wzywanie potworów przez konsolę
* Działający ekwipunek (otrzymywanie przedmiotów/postaci, ulepszanie przedmiotów/postaci, itp)

## Poradnik uruchamiania

**Uwaga:** Dla dodatkowej pomocy dołącz na nasz [Discord](https://discord.gg/T5vZU6UyeG).

### Wymagania

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Uwaga:** Jeśli chcesz tylko **uruchomić** serwer, samo **jre** powinno wystarczyć.

* [MongoDB](https://www.mongodb.com/try/download/community) (rekomendowane 4.0+)

* Aplikacja proxy: mitmproxy (mitmdump, rekomendowane), Fiddler Classic, itp.

### Uruchamianie

**Uwaga:** Jeśli aktualizujesz ze starszej wersji, usuń `config.json` aby wygenerować go ponownie.

1. Zdobądź `grasscutter.jar`
   - Pobierz z [akcji](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Lub zbuduj to samemu](#Budowanie)
2. Utwórz folder `resources` w tym samym folderze gdzie znajduje się grasscutter.jar oraz przenieś foldery `BinOutput` i `ExcelBinOutput` do folderu `resources` *(Sprawdź na [wiki](https://github.com/Grasscutters/Grasscutter/wiki) skąd możesz je pozyskać).*
3. Uruchom Grasscuttera komendą `java -jar grasscutter.jar`. **Upewnij się, że mongodb service działa w tle.**

### Łączenie się z klientem

½. Utwórz konto za pomocą [komend konsoli serwera](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Przekieruj połączenia: (wybierz jedno)
    - mitmdump: `mitmdump -s proxy.py -k`

      Certyfikat CA:

      ​	**Uwaga:** CA certyfikat zazwyczaj znajduje się w `%USERPROFILE%\.mitmproxy`, albo możesz pobrać go stąd `http://mitm.it`

      ​	Naciśnij podwójnie, aby [zainstalować](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) lub ...

      - Za pomocą wierszu poleceń (lub PowerShella) wpisz

        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```

    - Fiddler Classic: Uruchom Fiddler Classic, włącz `Decrypt https traffic` w ustawieniach oraz zmień domyślny port (Tools -> Options -> Connections) na dowolny inny niż `8888`, i wczytaj [ten skrypt](https://github.lunatic.moe/fiddlerscript) (w polu FiddlerScript).

    - [Plik hosts](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)

2. Ustaw serwer proxy na `127.0.0.1:8080` albo inny wybrany przez ciebie port.

**Możesz także użyć `start.cmd` aby uruchomić serwer gry i proxy, ale do tego musisz ustawić środowisko JAVA_HOME**

### Budowanie

Grasscutter używa Gradle, aby zajął się wymaganymi pakietami i kompilowaniem.

**Wymagania:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Konfigurowanie środowiska
.\gradlew jar # Kompilowanie
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Kompilowanie
```

Gotowy plik `jar` możesz znaleźć w głównym folderze Grasscuttera.

### Komendy zostały przeniesione do [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Rozwiązywanie problemów

* Jeśli kompilowanie się nie powiodło, sprawdź swoje zainstalowane JDK (JDK 17 oraz wartość ścieżki (PATH) folderu bin należącego do JDK)
* Mój klient nie może się połączyć, nie działa logowanie, 4206, itp... - Prawdopodobnie twoje proxy jest *problemem*, jeśli używasz Fiddlera upewnij się, że działa na innym porcie niż 8888
* Sekwencja, którą powinieneś uruchamiać: MongoDB > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Game
