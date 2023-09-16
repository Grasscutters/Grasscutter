![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**Attenzione:** Diamo sempre il benvenuto ai contributori del progetto. Prima di contribuire, leggi attentamente il nostro [Codice di condotta](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Funzionalità attuali

* Login
* Combattimento
* Lista di amici
* Teletrasporto
* Sistema Gacha
* Cooperativa *parzialmente* funzionale
* Evocazione nemici usando la console
* Inventario (ricevi e aggiorna oggetti/personaggi, ecc.)

## Guida rapida all'installazione

**Nota:** Se hai bisogno di aiuto, unisciti al nostro [Discord](https://discord.gg/T5vZU6UyeG).

### Requisiti

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Nota:** se vuoi solo **eseguirlo**, **jre** è sufficiente.

* [MongoDB](https://www.mongodb.com/try/download/community) (consigliato 4.0+)

* Servizi proxy: mitmproxy (mitmdump, consigliato), Fiddler Classic, ecc.

### Esecuzione

**Nota:** Se hai aggiornato da una versione precedente, elimina `config.json` in modo che venga generato di nuovo.

1. Ottieni "grasscutter.jar".
   - Scarica da [azioni](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Compilalo tu stesso](#Compilazione)
2. Crea una cartella chiamata `resources` nella directory in cui si trova grasscutter.jar e sposta lì le cartelle `BinOutput` ed `ExcelBinOutput` *(Vedi il [wiki](https://github.com/Grasscutters/Grasscutter/wiki ) per maggiori dettagli su come ottenerli.)*
3. Esegui Grasscutter con `java -jar grasscutter.jar`. **Assicurati che il servizio mongodb sia attivo.**

### Connessione al client

½. Crea un account usando [il comando corrispondente nella console del server](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Reindirizza il traffico: (scegli uno)
    - Con mitmdump: `mitmdump -s proxy.py -k`

      Autorizza il certificato CA:

      ​ **Nota:**Il certificato CA si trova solitamente in `%USERPROFILE%\ .mitmproxy`, oppure puoi scaricarlo da `http://mitm.it`

      Fai doppio clic su [installa](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) o ...

      - Con riga di comando

        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```

    - Con Fiddler Classic: esegui Fiddler Classic, abilita `Decrypt https traffic` nelle opzioni e cambia la porta predefinita in (Strumenti -> Opzioni -> Connessioni) in qualcosa di diverso da `8888`, e carica [questo script](https://github.lunatic.moe/fiddlerscript).

    - [File host](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)

2. Imposta il proxy di rete a `127.0.0.1:8080` o la porta proxy impostata.

**Puoi anche usare `start.cmd` per avviare automaticamente il server e il servizio proxy, ma devi impostare l'ambiente JAVA_HOME**

### Compilazione

Grasscutter usa Gradle per gestire le dipendenze e le build.

**Requisiti:**

- [Kit di sviluppo Java SE - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd grasscutter
.\gradlew.bat # Impostazioni dell'ambiente
.\gradlew jar # Compila
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd grasscutter
chmod +x gradlew
./gradlew jar # Compila
```

Puoi trovare il jar generato nella cartella principale del progetto.

### I comandi sono stati spostati al [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Soluzioni agli errori comuni

* Se la compilazione non riesce, controlla l'installazione di JDK (JDK 17 e convalida la variabile JDK bin PATH)
* Se il tuo client non si connette, non accede, da errore 4206, ecc... - Probabilmente *il problema* sono le tue impostazioni proxy, se usi
  Fiddler assicurati di utilizzare una porta diversa da 8888
* Sequenza di avvio: MongoDB > Grasscutter > Servizio proxy (mitmdump, fiddler, ecc.) > Gioco
