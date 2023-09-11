![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

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

## Guide d'installation rapide

**Note:** Pour obtenir un support, rejoignez notre serveur [Discord](https://discord.gg/T5vZU6UyeG) (en anglais).

### Démarage rapide (Automatique)

- Téléchargez Java 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- Téléchargez [MongoDB Community Server](https://www.mongodb.com/try/download/community)
- Téléchargez la version du jeu REL3.7 (Le client de jeut peut être obtenu ici si vous ne l'avez pas): https://github.com/MAnggiarMustofa/GI-Download-Library/blob/main/GenshinImpact/Client/3.7.0.md

- Téléchargez la [dernière version de Cultivation](https://github.com/Grasscutters/Cultivation/releases/latest). Ulilisez l'installateur en `.msi`.
- Après avoir ouvert Cultivation (en administrateur), appuyez sur le bouton de téléchargement en haut a droite. 
- Cliquez sur le bouton `Téléchargez tout-en-un`
- Cliquez sur l'engrenage dans le coin en haut a droite.
- Définisez l'emplacement d'installation du jeu.
- Définisez le chemin Java personnalisé à `C:\Program Files\Java\jdk-17\bin\java.exe`
- Laissez tous les autres paramètes par défauts

- Appuyez sur le bouton a coté de Lancer.
- Appuyez sur le bouton Lancer.
- Connectez vous avec le nom d'utilisateur que vous voulez. Le mot de passe n'a pas d'importance.

### Compilation

Grasscutter utilise Gradle pour la gestion des dépendances et la compilation.

**Prérequis**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou plus récent
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Setting up environments
.\gradlew jar # Compile
```

##### Linux (GNU)

```bash
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compile
```

Vous pouvez trouver le jar de sortie dans la racine du dossier du projet.

### Dépanage

Pour une liste des problèmes communs et leur solution et pour demander de l'aide, veuillez rejoindre [notre serveur Discord](https://discord.gg/T5vZU6UyeG) (en anglais) et dirigez vous vers le salon de support.
