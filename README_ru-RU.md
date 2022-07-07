
![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [HE](README_HE.md) | RU

**Внимание:** Мы всегда рады новому вкладу в проекте. Однако, перед тем, как сделать свой вклад, пожалуйста, прочтите наш [кодекс делового поведения](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Реализованные функции

* Авторизация
* Система боя
* Список друзей
* Телепортация
* Гача-система
* Кооп работает *частично*
* Спавн монстров через консоль
* Функции инвентаря (получение предметов/персонажей, улучшение предметов/персонажей, и т.п.)

## Краткое руководство по установке

**Заметка:** Для получения поддержки, присоединитесь к нашему серверу [Discord](https://discord.gg/T5vZU6UyeG).

### Требуется

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Заметка:** Для того, чтобы просто **запустить сервер**, достаточно только **jre**.

* [MongoDB](https://www.mongodb.com/try/download/community) (рекомендуются версии 4.0+)

* Прокси-демон: mitmproxy (mitmdump, рекомендуется), Fiddler Classic и т.п.

### Запуск

**Заметка:** При обновлении с более старой версии, удалите файл `config.json` для того, чтобы заново его сгенерировать.

1. Получите файл `grasscutter.jar` одним из следующих образов:
   - Скачайте напрямую со вкладки [Actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [Соберите самостоятельно](#Сборка)
2. Создайте папку `resources` в той же директории, что и grasscutter.jar, и переместите туда свои папки `BinOutput` и `ExcelBinOutput` *(Посетите [вики](https://github.com/Grasscutters/Grasscutter/wiki) для получения более подробной информации о том, где их найти.)*
3. Запустите Grasscutter с помощью команды `java -jar grasscutter.jar`. **Убедитесь, что в этот момент запущена служба mongodb.**

### Подключение с помощью клиента

½. Создайте аккаунт, введя [соответствующую команду в консоли сервера](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. Перенаправьте трафик: (выберите один из методов)
    - mitmdump: `mitmdump -s proxy.py -k`
    
      Доверьтесь сертификату CA:
    
      ​	**Заметка:**Обычно, сертификат CA хранится в папке `%USERPROFILE%\ .mitmproxy`. Также, вы можете скачать его с `http://mitm.it`
    
      ​	Два раза нажмите для [установки](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate), либо ...
    
      - Через командную строку
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: Запустите Fiddler Classic, включите настройку `Decrypt https traffic` в опциях и измените порт по умолчанию (Tools -> Options -> Connections) на что-то не равное `8888`, после чего запустите [этот скрипт](https://github.lunatic.moe/fiddlerscript).
      
    - [Файл hosts](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)
    
2. Установите прокси сети в `127.0.0.1:8080`, либо в тот порт прокси, который вы задали.

**Также, вы можете использовать `start.cmd` для автоматического запуска прокси-демонов и серверов, но для этого необходимо задать переменную среды JAVA_HOME**

### Сборка

Для сборки и решения проблем с зависимостями, Grasscutter использует Gradle.

**Требуется:**

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

Получившийся файл .jar можно найти в корневой папке проекта.

### Команды были перемещены на [вики](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# Краткое руководство по решению проблем

* Если не компилируется, то проверьте инсталляцию своего JDK (JDK 17 и валидированная переменная JDK bin PATH)
* Клиент не подключается, не входит, выдаёт ошибку 4206 и т.д. - Скорее всего, проблема в том, *как именно* вы настроили прокси-демонов. При использовании
  Fiddler убедитесь, что он запущен на любом порте, кроме 8888
* Порядок запуска: MongoDB > Grasscutter > Прокси-демон (mitmdump, fiddler и т.д.) > Игра
