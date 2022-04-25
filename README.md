![Grasscutter](https://socialify.git.ci/Grasscutters/Grasscutter/image?description=1&font=KoHo&forks=1&issues=1&language=1&name=1&owner=1&pattern=Circuit%20Board&pulls=1&stargazers=1&theme=Light)
<div style="text-align: center"><img alt="Documention" src="https://img.shields.io/badge/Wiki-Grasscutter-blue?style=for-the-badge&link=https://github.com/Grasscutters/Grasscutter/wiki&link=https://github.com/Grasscutters/Grasscutter/wiki"> <img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/Grasscutters/Grasscutter?logo=java&style=for-the-badge"> <img alt="GitHub" src="https://img.shields.io/github/license/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Grasscutters/Grasscutter?style=for-the-badge"> <img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/Grasscutters/Grasscutter/Build?logo=github&style=for-the-badge"></div>

<div style="text-align: center"><a href="https://discord.gg/T5vZU6UyeG"><img alt="Discord - Grasscutter" src="https://img.shields.io/discord/965284035985305680?label=Discord&logo=discord&style=for-the-badge"></a></div>

**Attention:** We always welcome contributors to the project. Before adding your contribution, please carefully read our [Code of Conduct](https://github.com/Grasscutters/Grasscutter/blob/stable/CONTRIBUTING.md).

## Current features

* Logging in
* Combat
* Friends list
* Teleportation
* Gacha system
* Co-op *partially* work
* Spawning monsters via console
* Inventory features (recieving items/characters, upgrading items/characters, etc)

## Quick setup guide

**Note:** for support please join our [Discord]()

### Requirements

* Java - SE ([mirror link](https://github.com/adoptium/temurin16-binaries/releases/tag/jdk-16.0.2+7) since Oracle required an account to download old builds)

  **Note:** If you just want to **run it**, then **jre** is fine 

* MongoDB (recommended 4.0+)

* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Running

**Note:** If you update from an older version, delete `config.json` for regeneration

1. Get `grasscutter.jar`
   - Download from [actions](https://nightly.link/Grasscutters/Grasscutter/workflows/build/stable/Grasscutter.zip)
   - [Build by yourself](#Building)
2. Create a `resources` folder in the directory where grasscutter.jar is located and bring your `BinOutput` and `ExcelBinOutput` folders into it *(Check the [wiki](https://github.com/Grasscutters/Grasscutter/wiki) for more details how to get those.)*
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

**you can also use `run.cmd` to start servers and proxy daemons automatically**

### Building

Grasscutter uses Gradle to handle dependencies & building.

**Requirements:**

- Java Development Kits - 8u202
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

You can find the output jar in the root of the project folder

## Commands

You might want to use this command (`java -jar grasscutter.jar -handbook`) in a cmd that is in the grasscutter folder. It will create a handbook file (GM Handbook.txt) where you can find the item IDs for stuff you want

There is a dummy user named "Server" in every player's friends list that you can message to use commands. Commands also work in other chat rooms, such as private/team chats. to run commands ingame, you need to add prefix `/` or `!` such as `/pos`

| Commands       | Usage                                             | Permission node           | Availability | description                                                  | Alias                                           |
| -------------- | ------------------------------------------------- | ------------------------- | ------------ | ------------------------------------------------------------ | ----------------------------------------------- |
| account        | account <create\|delete> <username> [uid]         |                           | Server only  | Creates an account with the specified username and the in-game uid for that account. The uid will be auto generated if not set. |                                                 |
| broadcast      | broadcast <message>                               | server.broadcast          | Both side    | Sends a message to all the players.                          | b                                               |
| changescene    | changescene <scene id>                            | player.changescene        | Client only  | Switch scenes by scene ID.                                   | scene                                           |
| clearartifacts | clearartifacts                                    | player.clearartifacts     | Client only  | Deletes all unequipped and unlocked level 0 artifacts, including yellow rarity ones from your inventory. | clearart                                        |
| clearweapons   | clearweapons                                      | player.clearweapons       | Client only  | Deletes all unequipped and unlocked weapons, including yellow rarity ones from your inventory. | clearwpns                                       |
| drop           | drop <itemID\|itemName> [amount]                  | server.drop               | Client only  | Drops an item around you.                                    | `d` `dropitem`                                  |
| give           | give [player] <itemId\|itemName> [amount] [level] | player.give               | Both side    | Gives item(s) to you or the specified player.                | `g` `item` `giveitem`                           |
| givechar       | givechar <uid> <avatarId> [level]                 | player.givechar           | Both side    | Gives the player a specified character.                      | givec                                           |
| godmode        | godmode [uid]                                     | player.godmode            | Client only  | Prevents you from taking damage.                             |                                                 |
| heal           | heal                                              | player.heal               | Client only  | Heal all characters in your current team.                    | h                                               |
| help           | help [command]                                    |                           | Both side    | Sends the help message or shows information about a specified command. |                                                 |
| kick           | kick <player>                                     | server.kick               | Both side    | Kicks the specified player from the server. (WIP)            | k                                               |
| killall        | killall [playerUid] [sceneId]                     | server.killall            | Both side    | Kill all entities in the current scene or specified scene of the corresponding player. |                                                 |
| list           | list                                              |                           | Both side    | List online players.                                         |                                                 |
| permission     | permission <add\|remove> <username> <permission>  | *                         | Both side    | Grants or removes a permission for a user.                   |                                                 |
| position       | position                                          |                           | Client only  | Get coordinates.                                             | pos                                             |
| reload         | reload                                            | server.reload             | Both side    | Reload server config                                         |                                                 |
| resetconst     | resetconst [all]                                  | player.resetconstellation | Client only  | Resets the constellation level on your current active character, will need to relog after using the command to see any changes. | resetconstellation                              |
| restart        |                                                   |                           | Both side    | Restarts the current session                                 |                                                 |
| say            | say <player> <message>                            | server.sendmessage        | Both side    | Sends a message to a player as the server                    | `sendservmsg` `sendservermessage` `sendmessage` |
| setfetterlevel | setfetterlevel <level>                            | player.setfetterlevel     | Client only  | Sets your fetter level for your current active character     | setfetterlvl                                    |
| setstats       | setstats <stat> <value>                           | player.setstats           | Client only  | Set fight property for your current active character         | stats                                           |
| setworldlevel  | setworldlevel <level>                             | player.setworldlevel      | Client only  | Sets your world level (Relog to see proper effects)          | setworldlvl                                     |
| spawn          | spanw <entityID\|entityName> [level] [amount]     | server.spawn              | Client only  | Spawns an entity near you                                    |                                                 |
| stop           | stop                                              | server.stop               | Both side    | Stops the server                                             |                                                 |
| talent         | talent <talentID> <value>                         | player.settalent          | Client only  | Set talent level for your current active character           |                                                 |
| teleport       | teleport <x> <y> <z>                              | player.teleport           | Client only  | Change the player's position.                                | tp                                              |
| weather        | weather <weatherID> <climateID>                   | player.weather            | Client only  | Changes the weather                                          | w                                               |

### Bonus

When you want to teleport to somewhere, use the ingame marking function on Map, click Confirm. You will see your
character falling from a very high destination, exact location that you marked.

# Quick Troubleshooting

* If compiling wasn't successful, please check your JDK installation (must be JDK 8 and validated JDK's bin PATH
  variable)
* My client doesn't connect, doesn't login, 4206, etc... - Mostly your proxy daemon setup is *the issue*, if using
  Fiddler make sure it running on another port except 8888
* Startup sequence: Mongodb > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Client
