![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

[EN](README.md) | 中文

**注意:** 我们一直欢迎您成为该项目的贡献者。在添加您的代码之前，请仔细阅读我们的 [代码规范](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## 当前特性

* 登录
* 战斗
* 好友列表
* 传送系统
* 祈愿系统
* 从控制台生成魔物
* 多人游戏 *部分* 可用
* 物品栏相关 (接收物品/角色, 升级角色/武器等)

## 快速设置指南

**附:** 加入我们的 [Discord](https://discord.gg/T5vZU6UyeG) 获取更多帮助！

### 环境需求

* Java SE - 17 (当您没有Oracle账户，可以使用[镜像](https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jdk/))

  **注:** 如果您仅仅想要简单地**运行服务端**, 那么使用 **jre** 便足够了 

* MongoDB (推荐 4.0+)

* Proxy daemon: mitmproxy (推荐使用mitmdump), Fiddler Classic, 等

### 运行

**注:** 如果您从旧版本升级到新版本，最好删除 `config.json` 并启动服务端jar来重新生成它

1. 获取 `grasscutter.jar`
   - 从 [actions](https://nightly.link/Grasscutters/Grasscutter/workflows/build/stable/Grasscutter.zip) 中下载
   - [自行构建](#构建)
2. 在**grasscutter.jar** 所在目录中创建 `resources` 文件夹并将 `BinOutput` 和 `ExcelBinOutput` 放入其中 *(查看 [wiki](https://github.com/Grasscutters/Grasscutter/wiki) 了解更多)*
3. 通过命令 `java -jar grasscutter.jar` 来运行Grasscutter. **在此之前请确认MongoDB服务运行正常**

### 连接

½. 在服务器控制台中 [创建账户](#命令列表).

1. 重定向流量: (选其一)
    - mitmdump:  `mitmdump -s proxy.py -k`
    
      信任 CA 证书:
    
      ​	**注:** mitmproxy的CA证书通常存放在 `% USERPROFILE%\ .mitmproxy`, 或者你也可以从`http://mitm.it` 中下载它
    
      ​	双击来[安装根证书](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) 或者..
    
      - 使用命令行
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: 运行Fiddler Classic, 在设置中开启 `解密https通信` 并将端口切换到除`8888` 以外的任意端口 (工具 -> 选项 -> 连接) 并加载 [此脚本](https://github.lunatic.moe/fiddlerscript).
      
    - [Hosts文件](https://github.com/Grasscutters/Grasscutter/wiki/Running#traffic-route-map)
    
2. 设置代理为 `127.0.0.1:8080` 或其它你所设定的端口

**你也可以简单地运行 `start.cmd` 来全自动启动服务端并设置代理**

### 构建

Grasscutter 使用 Gradle 来处理依赖及构建.

**依赖:**

- Java SE Development Kits - 17
- Git

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

你可以在项目根目录中找到`grasscutter.jar`

## 命令列表

你可能需要在终端中运行 `java -jar grasscutter.jar -handbook` 它将会创建一个 `GM Handbook.txt` 以方便您查阅物品ID等

你可能需要在终端中运行 `java -jar grasscutter.jar -gachamap` 来使得祈愿历史记录系统正常显示物品信息。 这个命令生成一个配置文件到如下文件夹：`GRASSCUTTER_RESOURCE/gcstatic`。 不执行此命令，您的祈愿历史记录中将只会显示数字ID而非物品名称。（目前仅支持自动生成英文记录信息）

在每个玩家的朋友列表中都有一个名为“Server”的虚拟用户，你可以通过发送消息来使用命令。命令也适用于其他聊天室，例如私人/团队聊天。
要在游戏中使用命令，需要添加 `/` 或 `!` 前缀，如 `/pos`

| 命令            | 用法                                         | 权限节点                  | 可用性   | 注释                                       | 别名                                            |
| -------------- | -------------------------------------------- | ------------------------- | -------- | ------------------------------------------ | ----------------------------------------------- |
| account        | account <create\|delete> <用户名> [uid]      |                           | 仅服务端 | 通过指定用户名和uid增删账户                |                                                 |
| broadcast      | broadcast <消息内容>                         | server.broadcast          | 均可使用 | 给所有玩家发送公告                         | b                                               |
| coop           | coop \<uid> <目标uid>                 | server.coop               | 均可使用    | 强制某位玩家进入指定玩家的多人世界                  |                                                 |
| changescene    | changescene <场景ID>                         | player.changescene        | 仅客户端 | 切换到指定场景                             | scene                                           |
| clear          | clear <all\|wp\|art\|mat> [UID]                     | player.clearinv     | 仅客户端 | 删除所有未装备及未解锁的圣遗物(art)或武器(wp)或材料(mat)或者所有(all),包括五星    | clear         |
| drop           | drop <物品ID\|物品名称> [数量]               | server.drop               | 仅客户端 | 在指定玩家周围掉落指定物品                 | `d` `dropitem`                                  |
| enterdungeon   | enterdungeon <地牢ID>                        | player.enterdungeon       | 仅客户端 | 进入某个地牢                                |                                                 |
| give           | give [uid] <物品ID\|物品名称> [数量] [等级] [精炼等级]  | player.give      | 均可使用 | 给予指定玩家一定数量及等级的物品 (精炼等级仅适用于武器)  | `g` `item` `giveitem`                  |
| givechar       | givechar \<uid> <角色ID> [等级]               | player.givechar           | 均可使用 | 给予指定玩家对应角色                       | givec                                           |
| giveart        | giveart [uid] \<圣遗物ID> \<主属性ID> [\<副属性ID>[,<次数>]]... [等级] | player.giveart            | 均可使用 | 给予玩家指定属性的圣遗物                   | gart                                           |
| giveall        | giveall [uid] [数量]                         | player.giveall            | 均可使用 | 给予指定玩家全部物品                       | givea                                           |
| godmode        | godmode [uid]                                | player.godmode            | 仅客户端 | 保护你不受到任何伤害(依然会被击退)         |                                                 |
| heal           | heal                                         | player.heal               | 仅客户端 | 治疗队伍中所有角色                         | h                                               |
| help           | help [命令]                                  |                           | 均可使用 | 显示帮助或展示指定命令的帮助               |                                                 |
| kick           | kick \<uid>                                   | server.kick               | 均可使用 | 从服务器中踢出指定玩家 (WIP)               | k                                               |
| killall        | killall [uid] [场景ID]                       | server.killall            | 均可使用 | 杀死指定玩家世界中所在或指定场景的全部生物 |                                                 |
| list           | list                                         |                           | 均可使用 | 列出在线玩家                               |                                                 |
| permission     | permission <add\|remove> <UID> <权限节点> | *                         | 均可使用 | 添加或移除玩家的权限                       |                                                 |
| position       | position                                     |                           | 仅客户端 | 获取当前坐标                               | pos                                             |
| reload         | reload                                       | server.reload             | 均可使用 | 重载服务器配置                             |                                                 |
| resetconst     | resetconst [all]                             | player.resetconstellation | 仅客户端 | 重置当前角色的命座,重新登录即可生效        | resetconstellation                              |
| restart        | restart                                      |                           | 均可使用 | 重启服务端                                 |                                                 |
| say            | say \<uid> <消息>                             | server.sendmessage        | 均可使用 | 作为服务器发送消息给玩家                   | `sendservmsg` `sendservermessage` `sendmessage` |
| setfetterlevel | setfetterlevel <好感等级>                    | player.setfetterlevel     | 仅客户端 | 设置当前角色的好感等级                     | `setfetterlvl` `setfriendship`                  |
| setstats       | setstats <属性> <数值>                       | player.setstats           | 仅客户端 | 直接修改当前角色的面板                     | stats                                           |
| setworldlevel  | setworldlevel <世界等级>                     | player.setworldlevel      | 仅客户端 | 设置世界等级(重新登录即可生效)             | setworldlvl                                     |
| spawn          | spawn <实体ID> [数量] [等级]                  | server.spawn              | 仅客户端 | 在你周围生成实体                           |                                                 |
| stop           | stop                                         | server.stop               | 均可使用 | 停止服务器                                 |                                                 |
| talent         | talent <天赋ID> <等级>                       | player.settalent          | 仅客户端 | 设置当前角色的天赋等级                     |                                                 |
| teleport       | teleport [@playerUid] \<x> \<y> \<z> [sceneId] | player.teleport           | 均可使用 | 传送玩家到指定坐标                         | tp                                              |
| tpall          |                                                   | player.tpall              | 仅客户端  | 传送多人世界中所有的玩家到自身地点         |                                                 |
| weather        | weather <天气ID> <气候ID>                    | player.weather            | 仅客户端 | 改变天气                                   | w                                               |

### 额外功能

当你想传送到某个地点, 只需要在地图中创建标记, 关闭地图后即可到达目标地点上空
- 传送
   - 当你想传送到某个地点时，可以使用游戏里的地图标记功能。
      - 用鱼钩（最后一个图标）在地图上标记一个点位。
      - （可选） 将标记名称改为数字，即可修改传送位置的Y坐标（高度，缺省值是300）。
      - 确认添加标记，并关闭地图。
      - 你会看到你的角色从你选定点位的正上方高空落下。

# 快速排除问题

* 如果编译未能成功,请检查您的jdk安装 (JDK 17并确认jdk处于环境变量`PATH`中
* 我的客户端无法登录/连接, 4206, 其它... - 大部分情况下这是因为您的代理存在问题.如果使用Fiddler请确认Fiddler监听端口不是`8888`
* 启动顺序: MongoDB > Grasscutter > 代理程序 (mitmdump, fiddler等.) > 客户端
