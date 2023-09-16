![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](docs/README_zh-CN.md) | [繁中](docs/README_zh-TW.md) | [FR](docs/README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**ध्यान:** हम हमेशा परियोजना में योगदानकर्ताओं का स्वागत करते हैं।. अपना योगदान जोड़ने से पहले कृपया हमारा ध्यानपूर्वक पढ़ें [आचार संहिता](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## वर्तमान सुविधाएँ

* लॉग इन करना
* युद्ध
* मित्रों की सूची
* टेलीपोर्टेशन
* गाचा प्रणाली
* सह-ऑप * आंशिक रूप से * काम करता है
* कंसोल के माध्यम से राक्षसों को जन्म देना
* इन्वेंट्री सुविधाएँ (आइटम / वर्ण प्राप्त करना, आइटम / वर्णों को अपग्रेड करना, आदि)

## त्वरित सेटअप गाइड

**टिप्पणी**: समर्थन के लिए कृपया हमसे जुड़ें [Discord](https://discord.gg/T5vZU6UyeG).

### त्वरित प्रारंभ (स्वचालित)

- Get Java 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

**ध्यान दें:** बस **सर्वर शुरू करने** के लिए, आपको बस **jre** की आवश्यकता है।
- Get [MongoDB Community Server](https://www.mongodb.com/try/download/community)

* प्रॉक्सी: मिटमडंप (अनुशंसित), मिटमप्रॉक्सी, फिडलर क्लासिक, आदि।
- गेम संस्करण REL3.7 प्राप्त करें (यदि आपके पास 3.7 क्लाइंट नहीं है तो उसे यहां पाया जा सकता है):: https://github.com/MAnggiarMustofa/GI-Download-Library/blob/main/GenshinImpact/Client/3.7.0.md

- डाउनलोड करें [latest Cultivation version](https://github.com/Grasscutters/Cultivation/releases/latest). उपयोग `.msi` इंस्टालरr.
- कलिवेशन (एडमिन के रूप में) खोलने के बाद, ऊपरी दाएं कोने में डाउनलोड बटन दबाएं।
- `डाउनलोड ऑल-इन-वन` पर क्लिक करें
- ऊपरी दाएं कोने में गियर पर क्लिक करें
- गेम इंस्टॉल पथ को उस स्थान पर सेट करें जहां आपका गेम स्थित है.
- कस्टम जावा पथ को इस पर सेट करें `C:\Program Files\Java\jdk-17\bin\java.exe`
- अन्य सभी सेटिंग्स को डिफ़ॉल्ट पर छोड़ दें

- लॉन्च करने के लिए आगे छोटे बटन पर क्लिक करें.
- लॉन्च बटन पर क्लिक करें.
- आप जो भी उपयोगकर्ता नाम चाहते हैं उसके साथ लॉग इन करें। पासवर्ड कोई मायने नहीं रखता.

### इमारत

ग्रासकटर निर्भरता और निर्माण को संभालने के लिए ग्रैडल का उपयोग करता है।

**आवश्यकताएं:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- [Git](https://git-scm.com/downloads)

#####  विंडोज

```shell
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # Setting up environments
.\gradlew jar # Compile
```

##### लिनक्स (जीएनयू)

```bash
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # Compile
```

आप आउटपुट जार को प्रोजेक्ट फ़ोल्डर के रूट में पा सकते हैं।.

### समस्या निवारण

सामान्य मुद्दों और समाधानों की सूची और सहायता मांगने के लिए कृपया शामिल हों [our Discord server](https://discord.gg/T5vZU6UyeG) और सपोर्ट चैनल पर जाएं.