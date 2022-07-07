![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | FR | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md)

**Attention:** De nouveaux contributeurs sont toujours les bienvenus. Avant d'ajouter votre contribution, veuillez lire le [code de conduite](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Fonctionnalités actuelles : 

* Connection
* Combat
* Liste d'amis
* Téléportation
* Système de gacha
* Le multijoueur fonctionne *partiellement*
* Apparition de monstres via la console
* Inventaire (obtention d'objets/de personnages, amélioration d'objets/personnages, etc)

## Guide de démarrage rapide

**Note:** Pour obtenir un support, rejoignez notre serveur [Discord](https://discord.gg/T5vZU6UyeG) (en anglais).

### Logiciels requis

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Note:** Si vous voulez juste **l'exécuter**, Alors vous pouvez télécharger seulement le **jre**

* MongoDB  (4.0+ recommandé)

* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Lancement

**Note:** Si vous avez mis à jour depuis une ancienne version, supprimez `config.json` pour le regénérer.

1. Obtenez `grasscutter.jar`
   - Téléchargez le depuis les [actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Buildez le par vous-même](#Building)
2. Créez un dossier `resources` dans le dossier où grasscutter.jar est situé et déplacez vos dossiers `BinOutput` et `ExcelBinOutput` ici *(Vérifiez le [wiki](https://github.com/Grasscutters/Grasscutter/wiki) pour plus de détails sur comment les obtenir.)*
3. Exécutez Grasscutter avec `java -jar grasscutter.jar`. **Soyez sûr que le service MongoDB est en cours d'exécution.**

### Connection avec le client

½. Créez un compte avec la [console de commande du serveur](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Redirection du traffic: (Choisissez-en un)
    - mitmdump: `mitmdump -s proxy.py -k`
    
      Approuvez le certificat CA:
    
      ​	**Note:**Le certificat CA est généralement stocké sous `%USERPROFILE%\ .mitmproxy`, ou vous pouvez le télécharger depuis `http://mitm.it`
    
      ​	Double-cliquez pour [installer](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) oo ...
    
      - Via la ligne de commande
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: Exécutez Fiddler Classic, Activez `Decrypt https traffic` dans les paramètres et changez le port par défaut ici (Tools -> Options -> Connections) à autre chose que `8888`, et chargez [ce script](https://github.lunatic.moe/fiddlerscript).
      
    - [Fichier hosts](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)
    
2. Définissez le proxy du réseau comme `127.0.0.1:8080` ou le port du proxy que vous avez spécifié.

**Vous pouvez aussi utiliser `start.cmd` to démarrer les serveurs et le proxy automatiquement, mais vous devez mettre en place la variable d'environnement JAVA_HOME**

### Building

Grasscutter utilise Gradle pour gérer les dépendances et la construction.

**Logiciels requis:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Mettre en place l'environnement
.\gradlew jar # Compiler
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compiler
```

Vous trouverez le fichier jar compilé à la racine du dossier du projet.

### Les commandes ont été déplacé vers le [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands)! (en anglais)
 
# Dépannage rapide

* Si la compilation a échoué, veuillez vérifier votre installation de votre JDK (JDK 17 et le bon dossier bin du JDK dans la variable PATH)
* Mon client ne se connecte pas au serveur, impossible de se connecter a mon compte, 4206, etc... - La plupart du temps, *le problème* vient de la configuration de votre proxy. Si vous utilisez Fiddler, vérifiez s'il est exécuté sur un port autre que 8888 
* Séquence de démarrage : MongoDB > Grasscutter > Proxy (mitmdump, fiddler, etc...) > Jeu
