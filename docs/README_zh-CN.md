![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md)

**请注意:** 欢迎成为本项目的贡献者。但在提交 PR 之前, 请仔细阅读 [代码规范](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md)。

## 当前功能

* 登录
* 战斗
* 好友
* 传送
* 祈愿
* 多人游戏 *部分* 可用
* 从控制台生成魔物
* 物品 (接收或升级角色、武器等)

## 快速设置指南

**注意:** 如需帮助请加入 [Discord](https://discord.gg/T5vZU6UyeG)

### 环境需求

* [Java SE - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

  **注意:** 如果想仅**运行服务端**, 只下载 **jre** 即可

* [MongoDB](https://www.mongodb.com/try/download/community) (推荐 4.0+)

* 代理程序: [mitmproxy](https://mitmproxy.org/) (仅需 mitmdump；推荐使用), [Fiddler Classic](https://telerik-fiddler.s3.amazonaws.com/fiddler/FiddlerSetup.exe) 等

### 运行服务端

**注意:** 从旧版本升级到新版本, 需要删除 `config.json` 使其重新生成

1. 获取 `grasscutter.jar`
   - 从 [actions](https://github.com/Grasscutters/Grasscutter/actions) 下载，或 [自行编译](#构建)
2. 在 JAR 文件根目录中创建 `resources` 文件夹并复制 `BinOutput` 和 `ExcelBinOutput` *(查看 [Wiki](https://github.com/Grasscutters/Grasscutter/wiki) 了解更多)*
3. **确认 MongoDB 服务运行正常后**，使用命令行 `java -jar grasscutter.jar` 运行 Grasscutter。

### 客户端连接

½. 在服务器控制台 [创建账户](https://github.com/Grasscutters/Grasscutter/wiki/Commands#targeting)

1. 重定向流量: (选择其中一个)
    - mitmdump: `mitmdump -s proxy.py -k`

      信任 CA 证书:

      ​	**注意:** mitmproxy 的 CA 证书通常存放在 `%USERPROFILE%\ .mitmproxy`, 或者从 `http://mitm.it` 下载证书

      ​ 双击 [安装根证书](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate)或者...

      - 使用命令行

        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```

    - Fiddler Classic: 运行 Fiddler Classic, 在设置中开启 `解密 https 通信` 并将端口设为除 `8888` 以外的任意端口 (工具 -> 选项 -> 连接) 并加载 [此脚本](https://github.lunatic.moe/fiddlerscript)

    - [Hosts 文件](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)

2. 设置代理为 `127.0.0.1:8080` 或你设置的端口

**也可直接运行 `start.cmd` 一键启动服务端并设置代理, 但设置 `JAVA_HOME` 环境变量并配置 `start_config.cmd`**

### 构建

Grasscutter 使用 Gradle 来处理依赖及编译。

**前置依赖:**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # 建立开发环境
.\gradlew jar # 编译
```

##### Linux

```bash
git clone https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # 编译
```

* 编译后的 JAR 文件会在源码根目录生成

### 命令列表请到 [wiki](https://github.com/Grasscutters/Grasscutter/wiki/Commands) 查看

# 快速问题排除

* 如果编译失败, 请检查 JDK 安装是否正确 (需要 JDK 17 并确认 JDK 的 bin 文件夹处于环境变量 `PATH` 中)
* 客户端无法登录、连接、错误 4206 等其他问题... - 大部分情况是因为代理设置出现了*问题*。
  如果使用 Fiddler，请确认 Fiddler 监听端口不是 `8888`
* 启动顺序: MongoDB > Grasscutter > 代理程序 (mitmdump, Fiddler 等) > 客户端
