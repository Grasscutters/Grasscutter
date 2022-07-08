![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | [简中](README_zh-CN.md) | 繁中 | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md)

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

### 環境需求

* Java SE - 17 ([連結](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **注意:** 如果僅想**執行服務端**, 使用 **jre** 即可

* [MongoDB](https://www.mongodb.com/try/download/community) (推薦 4.0+)

* 代理程式: mitmproxy (推薦 mitmdump), Fiddler Classic 等

### 執行

**注意:** 從舊版本升級到新版本, 需要刪除 `config.json`

1. 獲取 `grasscutter.jar`
   - 從 [actions](https://github.com/Grasscutters/Grasscutter/suites/6895963598/artifacts/267483297) 下載
   - [自行編譯](#編譯)
2. 在 JAR 檔案根目錄中建立 `resources` 資料夾並複製 `BinOutput` 和 `ExcelBinOutput` *(查看 [wiki](https://github.com/Grasscutters/Grasscutter/wiki) 瞭解更多)*
3. 命令列 `java -jar grasscutter.jar` 執行 Grasscutter。**在此之前請確認 MongoDB 服務執行正常**

### 客戶端連線

½. 在伺服器控制台[建立賬戶](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting)

1. 重定向流量: (選擇其中一個)
    - mitmdump: `mitmdump -s proxy.py -k`
    
      信任 CA 證書:
    
      ​	**注意:** mitmproxy 的 CA 證書通常存放在 `%USERPROFILE%\ .mitmproxy`, 或者在 `http://mitm.it` 下載證書
    
      ​ 雙擊[安裝根證書](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate)或者...
    
      - 使用命令列
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: 執行 Fiddler Classic, 在設定中開啟 `解密 https 通訊` 並將通訊埠設為除 `8888` 以外的任意通訊埠 (工具 -> 選項 -> 連線) 並載入[此指令碼](https://github.lunatic.moe/fiddlerscript)
      
    - [Hosts 檔案](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)
    
2. 設定代理為 `127.0.0.1:8080` 或你設定的通訊埠

**也可直接執行 `start.cmd` 一鍵啟動服務端並設定代理, 但必須設定 `JAVA_HOME` 環境變數**

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
