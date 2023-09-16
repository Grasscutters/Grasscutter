![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**תשומת לב בבקשה:** אנחנו מקבלים עזרה בפיתוח התוכנה. לפני שאתם תורמים לפרויקט בבקשה תקראו את [תנאי השימוש](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## אפשרויות זמינות כרגע

* כניסה למשחק ומשתמש
* לחימה
* רשימת חברים
* טלפורטים ברחבי המפה
* מערכת הווישים להשגת דמויות
* קו-אופ (אפשרות לשחק עם חברים) *חלקית* עובדת
* זימון אויבים באמצעות פקודות
* אפשרויות של מלאי ציוד במשחק (קבלת נשקים/דמויות, שדרוג נשקים/דמויות וכו'

## הוראות הפעלה בסיסיות

**חשוב מאוד:** בשביל לקבל תמיכה תכנסו לשרת [הדיסקורד](https://discord.gg/T5vZU6UyeG) שלנו.

### דרישות להפעלה

* Java SE - 17 ([קישור](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **הערה:** אם אתם רוצים רק **להריץ את זה כבר מוכן** אז **jre** בלבד זה בסדר

* [MongoDB](https://www.mongodb.com/try/download/community) (מומלץ 4.0 ומעלה)

* Proxy daemon: mitmproxy (mitmdump מומלץ), Fiddler Classic, וכו'.

### הפעלה עצמה

**הערה חשובה:** אם אתם מעדכנים את השרת מגרסה ישנה אז תמחקו את הקובץ `config.json` בשביל ליצור אותו מחדש מעודכן

1. להשיג `grasscutter.jar`
   - להוריד אחד מוכן מתוך [מוכנים](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297)
   - [לבנות אחד בעצמך](#בנייה)

2. תצרו תיקייה בשם `resources` בתוך התיקייה איפה שהקובץ grasscutter.jar נמצא ותעבירו את התיקיות `BinOutput` ו- `ExcelBinOutput` לשם *(תקראו את [המדריך המלא](https://github.com/Grasscutters/Grasscutter/wiki) בשביל לקבל יותר מידע לגבי אלה.)*

3. תריצו את השרת בעזרת פקודה `java -jar grasscutter.jar`. **תדאגו שהשירות mongodb פועל באותו הזמן ברקע**


### התחברות עם המשחק

½. תצרו משתמש משחק על ידי שימוש ב[פקודות השרת](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting).

1. תעבירו את הניתוב: (תבחרו אחת מהדרכים)
    - שימוש בmitmdump: פקודת `mitmdump -s proxy.py -k` בשורת פקודה

    אישור שימוש החוזה שלהם:

   ​ **הערה חשובה:** החוזה בדרך כלל נמצא בתוך התיקייה `%USERPROFILE%\ .mitmproxy`, או שאתם יכולים להוריד את זה מהאתר `http://mitm.it`

   ​ תלחצו פעמיים בשביל [הורדה](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) או...

- על ידי שימוש בשורת הפקודה
```shell
certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
```
  - שימוש בFiddler Classic: תריצו Fiddler Classic, תפעילו את ההגדרה `Decrypt https traffic` בהגדרות ותשנו את הפורט ברירת מחדל לכל מספר שהוא לא `8888`, ותפעילו את [הסקריפט הזה](https://github.lunatic.moe/fiddlerscript).



   -עריכת [קובץ הHosts](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)

2. תשנו את שרת בproxy שלכם ל`127.0.0.1:8080`

**אתם יכולים גם להשתמש בקובץ `start.cmd` בשביל להפעיל את כל השרתים באופן אוטומטי אבל תצטרכו להגדיר JAVA_HOME בסביבות המחדל במערכת באופן ידני**


### בנייה
התוכנה משתמשת בGradle בשביל לבנות את כל השרת


**דרישות:**


- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # בשביל להוריד את כל חבילות הבנייה
.\gradlew jar # בשביל הבנייה עצמה
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # בנייה עצמה
```

אתם יכולים למצוא את קובץ התוצר הסופי grasscutter.jar באותה התיקייה שבו עשיתם את הכל

### הפקודות הועברו ל[ויקיפדיה שלנו](https://github.com/Grasscutters/Grasscutter/wiki/Commands)!

# פתרון בעיות מהיר:

* אם בניית השרת לא הצליחה, תבדקו את מיקום ההתקנה של JDK שלכם (JDK 17 והPATH של הJDK בסביבות המשתנים במערכת)
* המשחק לא מתחבר לי לשרת, לא נכנס למשתמש, 4206, וכו'... - לרוב המקרים התקנת הproxy שלכם היא הבעייתית, אם משתמשים בFiddler אז תדאגו שאתם משתמשים בכל פורט שהוא לא 8888
* סדר ההפעלות: MongoDB, ואז Grasscutter, ואז שרתי הproxy שלכם (mitmpump, fiddler וכו'), ורק אז המשחק עצמו
