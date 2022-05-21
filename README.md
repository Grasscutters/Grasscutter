![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&forks=1&issues=1&language=1&logo=https%3A%2F%2Fs2.loli.net%2F2022%2F04%2F25%2FxOiJn7lCdcT5Mw1.png&name=1&owner=1&pulls=1&stargazers=1&theme=Light)
<div align="center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div align="center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

EN | [中文](README_zh-CN.md)

**Attention:** We always welcome contributors to the project. Before adding your contribution, please carefully read our [Code of Conduct](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Current features

* Logging in
* Combat
* Friends list
* Teleportation
* Gacha system
* Co-op *partially* works
* Spawning monsters via console
* Inventory features (recieving items/characters, upgrading items/characters, etc)

## Quick setup guide

**Note:** For support please join our [Discord](https://discord.gg/T5vZU6UyeG).

### Requirements

* Java SE - 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))

  **Note:** If you just want to **run it**, then **jre** only is fine.

* MongoDB (recommended 4.0+)

* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Running

**Note:** If you updated from an older version, delete `config.json` to regenerate it.

1. Get `grasscutter.jar`
   - Download from [actions](https://nightly.link/Grasscutters/Grasscutter/workflows/build/stable/Grasscutter.zip)
   - [Build by yourself](#Building)
2. Create a `resources` folder in the directory where grasscutter.jar is located and move your `BinOutput` and `ExcelBinOutput` folders there *(Check the [wiki](https://github.com/Grasscutters/Grasscutter/wiki) for more details how to get those.)*
3. Run Grasscutter with `java -jar grasscutter.jar`. **Make sure mongodb service is running as well.**

### Connecting with the client

½. Create an account using [server console command](#Commands).

1. Redirect traffic: (choose one)
    - mitmdump: `mitmdump -s proxy.py -k`
    
      Trust CA certificate:
    
      ​	**Note:**The CA certificate is usually stored in `% USERPROFILE%\ .mitmproxy`, or you can download it from `http://mitm.it`
    
      ​	Double click for [install](https://docs.microsoft.com/en-us/skype-sdk/sdn/articles/installing-the-trusted-root-certificate#installing-a-trusted-root-certificate) or ...
    
      - Via command line
    
        ```shell
        certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
        ```
    
    - Fiddler Classic: Run Fiddler Classic, turn on `Decrypt https traffic` in setting and change the default port there (Tools -> Options -> Connections) to anything other than `8888`, and load [this script](https://github.lunatic.moe/fiddlerscript).
      
    - [Hosts file](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)
    
2. Set network proxy to `127.0.0.1:8080` or the proxy port you specified.

**you can also use `start.cmd` to start servers and proxy daemons automatically**

### Building

Grasscutter uses Gradle to handle dependencies & building.

**Requirements:**

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

You can find the output jar in the root of the project folder.

## Commands

You might want to use this command (`java -jar grasscutter.jar -handbook`) in a cmd that is in the grasscutter folder. It will create a handbook file (GM Handbook.txt) where you can find the item IDs for stuff you want

You may want to use this command (`java -jar grasscutter.jar -gachamap`) to generate a mapping file for the gacha record subsystem. The file will be generated to `GRASSCUTTER_RESOURCE/gcstatic` folder. Otherwise you may only see number IDs in the gacha record page.

There is a dummy user named "Server" in every player's friends list that you can message to use commands. Commands also work in other chat rooms, such as private/team chats. to run commands ingame, you need to add prefix `/` or `!` such as `/pos`

### Targeting
 1. For commands that target a Player, you can specify a target UID with `@UID` as an argument in any position to the command.
 2. If you message a valid command at another player (instead of at the "Server" virtual player), they will be the target for that command if you didn't set one above.
 3. If none of the above, it will default to a persistent target player you previously set using the command `/target <UID>`.
 4. If none of the above, you will be the target of the command. If you are entering the command from the Server console, **it will not work!**

 Note that performing commands on other players will usually require different a permission to the base permission node. e.g. `player.give` becomes `player.give.others` if used on another player.

| Commands       | Description                                                                                       | Alias              | Targeting     | Usage                                                                       | Permission node           |
| -------------- | ------------------------------------------------------------------------------------------------- | ------------------ | ------------- | --------------------------------------------------------------------------- | ------------------------- |
| account        | Creates an account with the specified username, and the in-game UID if specified.                 |                    | Server only   | account \<create\|delete> \<username> [UID]                                 |                           |
| broadcast      | Sends a message to all the players.                                                               | b                  | None          | broadcast \<message>                                                        | server.broadcast          |
| coop           | Forces someone to join the world of others.                                                       |                    | Online Player | coop [host UID (default self)]                                              | server.coop               |
| changescene    | Switch scenes by scene ID.                                                                        | scene              | Online Player | changescene \<scene id>                                                     | player.changescene        |
| clear          | Deletes all unequipped and unlocked lvl0 artifacts(art)/weapons(wp)/material(mat) from inventory. |                    | Online Player | clear \<all\|wp\|art\|mat>                                                  | player.clearinv           |
| drop           | Drops an item around you.                                                                         | d dropitem         | Online Player | drop \<itemID\|itemName> [amount]                                           | server.drop               |
| enterdungeon   | Enter a dungeon by dungeon ID.                                                                    |                    | Online Player | enterdungeon \<dungeon id>                                                  | player.enterdungeon       |
| give           | Gives item(s) to you or the specified player.                                                     | g item giveitem    | Online Player | give \<itemId\|itemName> [amount] [level] [refinement]                      | player.give               |
| giveall        | Gives all items.                                                                                  | givea              | Online Player | giveall [amount]                                                            | player.giveall            |
| giveart        | Gives the player a specified artifact.                                                            | gart               | Online Player | giveart \<artifactId> \<mainPropId> [\<appendPropId>[,\<times>]]... [level] | player.giveart            |
| givechar       | Gives the player a specified character.                                                           | givec              | Online Player | givechar \<avatarId>                                                        | player.givechar           |
| godmode        | Prevents you from taking damage.                                                                  |                    | Online Player | godmode                                                                     | player.godmode            |
| heal           | Heals all characters in your current team.                                                        | h                  | Online Player | heal                                                                        | player.heal               |
| help           | Sends the help message or shows information about a specified command.                            |                    | None          | help [command]                                                              |                           |
| kick           | Kicks the specified player from the server.                                                       | k                  | Online Player | kick                                                                        | server.kick               |
| killall        | Kills all entities in the current scene or specified scene of the corresponding player.           |                    | Online Player | killall [sceneId]                                                           | server.killall            |
| list           | Lists online players.                                                                             |                    | None          | list                                                                        |                           |
| permission     | Grants or removes a permission for a user.                                                        |                    | Online Player | permission \<add\|remove> \<permission>                                     | permission                |
| position       | Sends your current coordinates.                                                                   | pos                | Online Player | position                                                                    |                           |
| reload         | Reloads the server config.                                                                        |                    | None          | reload                                                                      | server.reload             |
| resetconst     | Resets currently selected (or all) character(s) to C0. Relog to see proper effects.               | resetconstellation | Online Player | resetconst [all]                                                            | player.resetconstellation |
| restart        | Restarts the current session.                                                                     |                    | None          | restart                                                                     |                           |
| sendmessage    | Sends a message to a player as the server.                                                        | say                | Online Player | say \<message>                                                              | server.sendmessage        |
| setfetterlevel | Sets the friendship level for your currently selected character.                                  | setfetterlvl       | Online Player | setfetterlevel \<level>                                                     | player.setfetterlevel     |
| setstats       | Sets a stat for your currently selected character.                                                | stats              | Online Player | setstats \<stat> \<value>                                                   | player.setstats           |
| setworldlevel  | Sets your world level. Relog to see proper effects.                                               | setworldlvl        | Online Player | setworldlevel \<level>                                                      | player.setworldlevel      |
| spawn          | Spawns some entities around you.                                                                  |                    | Online Player | spawn \<entityId> [amount] [level(monster only)]                            | server.spawn              |
| stop           | Stops the server.                                                                                 |                    | None          | stop                                                                        | server.stop               |
| talent         | Sets talent level for your currently selected character                                           |                    | Online Player | talent \<talentID> \<value>                                                 | player.settalent          |
| team           | Add, remove, or swap avatars in your current team. Index start from 1.                            |                    | Online Player | team \<add\|remove\|set> [avatarId,...] [index|first|last|index-index,...]  | player.team               |
| teleport       | Change the player's position.                                                                     | tp                 | Online Player | teleport \<x> \<y> \<z> [sceneId]                                           | player.teleport           |
| tpall          | Teleports all players in your world to your position.                                             |                    | Online Player | tpall                                                                       | player.tpall              |
| unlocktower    | Unlock the all floors of abyss.                                                                   | ut                 | Online Player | ut                                                                          | player.tower              |
| weather        | Changes the weather.                                                                              | w                  | Online Player | weather \<weatherID> \<climateID>                                           | player.weather            |

### Bonus

- Teleporting
  - When you want to teleport to somewhere, use the in-game marking function on Map.
    - Mark a point on the map using the fish hook marking (the last one.)
    - (Optional) rename the map marker to a number to override the default Y coordinate (height, default 300.)
    - Confirm and close the map.
    - You will see your character falling from a very high destination, exact location that you marked.
 
# Quick Troubleshooting

* If compiling wasn't successful, please check your JDK installation (JDK 17 and validated JDK's bin PATH variable)
* My client doesn't connect, doesn't login, 4206, etc... - Mostly your proxy daemon setup is *the issue*, if using
  Fiddler make sure it running on another port except 8888

* Startup sequence: Mongodb > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Game
