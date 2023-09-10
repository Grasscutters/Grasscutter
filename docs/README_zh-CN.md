![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documentation" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Grasscutters/Grasscutter/build.yml?branch=development&logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](../README.md) | [简中](README_zh-CN.md) | [繁中](README_zh-TW.md) | [FR](README_fr-FR.md) | [ES](README_es-ES.md) | [HE](README_HE.md) | [RU](README_ru-RU.md) | [PL](README_pl-PL.md) | [ID](README_id-ID.md) | [KR](README_ko-KR.md) | [FIL/PH](README_fil-PH.md) | [NL](README_NL.md) | [JP](README_ja-JP.md) | [IT](README_it-IT.md) | [VI](README_vi-VN.md) | [हिंदी](README_hn-IN.md)

**注意:** 我们始终欢迎项目的贡献者。但在做贡献之前，请仔细阅读我们的[代码规范](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md)。

## 当前功能

* 登录
* 战斗
* 好友
* 传送
* 祈愿
* 多人游戏 *部分* 可用
* 从控制台生成魔物
* 背包功能（接收或升级物品、角色等）。
 
## 快速安装指南

**注意:** 如需帮助，请加入我们的[Discord](https://discord.gg/T5vZU6UyeG)。

### 快速开始（全自动）

- 获取Java 17：https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- 获取[MongoDB社区版](https://www.mongodb.com/try/download/community)
- 获取游戏4.0正式版 (如果你没有4.0的客户端，可以在这里找到）：https://github.com/MAnggiarMustofa/GI-Download-Library/blob/main/GenshinImpact/Client/4.0.0.md)

- 下载[最新的Cultivation版本](https://github.com/Grasscutters/Cultivation/releases/latest)（使用以“.msi”为后缀的安装包）。
- 以管理员身份打开Culivation，按右上角的下载按钮。
- 点击“下载 Grasscutter 一体化”
- 点击右上角的齿轮
- 将游戏安装路径设置为你游戏所在的位置。
- 将自定义Java路径设置为`C:\Program Files\Java\jdk-17\bin\java.exe`
- 保持所有其它设置为默认值

- 点击“启动”按钮旁边的小按钮。
- 点击“启动”按钮。
- 随便想一个用户名登录，不需要密码。

### 构建

Grasscutter使用Gradle来处理依赖和构建。

**前置：**

- [Java SE Development Kits - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)或更高版本
- [Git](https://git-scm.com/downloads)

##### Windows

```shell
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
.\gradlew.bat # 设置开发环境
.\gradlew jar # 编译
```

##### Linux（GNU）

```bash
git clone --recurse-submodules https://github.com/Grasscutters/Grasscutter.git
cd Grasscutter
chmod +x gradlew
./gradlew jar # 编译
```

你可以在项目的根目录找到输出的jar。

### 故障排除

获取常见问题的解决方案或寻求帮助，请加入[我们的Discord服务器](https://discord.gg/T5vZU6UyeG)并进入“support”频道。
