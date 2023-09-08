![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**請注意:** 歡迎成為本專案的貢獻者。在提交 PR 之前, 請仔細閱讀[程式碼規範](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md)。

## 當前功能

* 登入
* 戰鬥
* 好友列表
* 傳送系統
* 祈願系統
* 從控制台生成魔物
* 多人遊戲 *部分* 可用
* 物品欄相關 (接收物品/角色, 升級角色/武器等)

## 快速設定指南

**注意:** 如需幫助請加入 [Discord](https://discord.gg/T5vZU6UyeG)

### 快速開始（全自動）

- 下載 Java 17：https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- 下載 [MongoDB 社區伺服器](https://www.mongodb.com/try/download/community)
- 下載遊戲版本 REL3.7（如果你沒有的話，可以在[這裡](https://github.com/MAnggiarMustofa/GI-Download-Library/blob/main/GenshinImpact/Client/3.7.0.md)找到 3.7 客戶端）

- 下載 [最新的 Cultivation 版本](https://github.com/Grasscutters/Cultivation/releases/latest)。使用 `.msi` 安裝程式。
- 以管理員身分打開 Culivation，按右上角的下載按鈕。
- 點擊 `Download All-in-One`
- 點擊右上角的齒輪
- 將遊戲安裝路徑設置為你的遊戲所在的位置。
- 將自定義 Java 路徑設置為 `C:\Program Files\Java\jdk-17\bin\java.exe`
- 其他設置保持預設

- 點擊啟動旁邊的小按鈕。
- 點擊啟動按鈕。
- 用你想要的用戶名登錄，密碼無所謂。

### 編譯

Grasscutter 使用 Gradle 來處理依賴及編譯。

**依賴:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # 建立開發環境
.\gradlew jar # 編譯
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # 編譯
```

編譯後的 JAR 檔案存放在根目錄

### 命令列表請到 [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands) 查看

# 快速排除問題

* 如果編譯失敗, 請檢查 JDK 安裝是否正確 (要求 JDK 17 並確認 JDK 處於環境變數 `PATH` 中)
* 客戶端無法登入/連線, 4206, 其他問題... - 大部分情況是因為代理設定本身就是*問題*。
  如果使用 Fiddler 請確認 Fiddler 監聽通訊埠不是 `8888`
* 啟動順序: MongoDB > Grasscutter > 代理程式 (mitmdump, fiddler 等) > 客戶端
