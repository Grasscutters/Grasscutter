# Grasscutter
A WIP server reimplementation for *some anime game* 2.3-2.6

**Documentation**: [Grasscutter Wiki](https://github.com/Melledy/Grasscutter/wiki/)  
**Note**: For support please join the [Discord server](https://discord.gg/T5vZU6UyeG).
# Current features
* Logging in
* Combat
* Spawning monsters via console
* Inventory features (recieving items/characters, upgrading items/characters, etc)
* Gacha system
* Friends list
* Co-op *partially* work
# Quick setup guide
### Note
* If you update from an older version, delete `config.json` for regeneration

### Prerequisites
* JDK-8u202 ([mirror link](https://mirrors.huaweicloud.com/java/jdk/8u202-b08/) since Oracle required an account to download old builds)
* Mongodb (recommended 4.0+)
* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Starting up Grasscutter server (Assuming you are on Windows)
1. Setup compile environment using `gradlew.bat`
2. Open cmd and go to the Grasscutter folder
3. Compile Grasscutter with `gradlew jar`
4. Create a folder named `resources` in your Grasscutter directory, bring your `BinOutput` and `ExcelBinOutput` folders into it *(Check the wiki for more details how to get those.)*
5. Run Grasscutter with the start.cmd file. Make sure you open MongoDB before you run the server

### Connecting with the client
½. Create an account using *server console command* below

`account create [username] {playerid}` - Creates an account with the specified username and the in-game uid for that account. The playerid parameter is optional and will be auto generated if not set.

1. Run a proxy daemon: (choose either one)
	- mitmdump: `mitmdump -s proxy.py -k`
	- Fiddler Classic: Run Fiddler Classic, turn on `Decrypt https traffic` in setting and change the default port there (Tools -> Options -> Connections) to anything other than `8888`, and load [this script](https://github.lunatic.moe/fiddlerscript).
2. Trust CA certificate:
	- mitmdump: `certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer`
2. Set network proxy to `127.0.0.1:8080` or the proxy port you specified.
4. *yoink*

### or you can use `start.cmd` file to start Server & Proxy daemon with one click

# Grasscutter commands

You might want to use this command (`java -jar grasscutter.jar -handbook`) in a cmd that is in the grasscutter folder. It will create a handbook file (GM Handbook.txt) where you can find the item IDs for stuff you want

There is a dummy user named "Server" in every player's friends list that you can message to use commands. Commands also work in other chat rooms, such as private/team chats. Example command: `!give 223 1000` 

`spawn [monster id] [level] [amount]`

`give [item id] [amount]`

`givechar [avatar id] [level]`

`drop [item id] [amount]`

`killall`

`setworldlevel [level]` - Relog to see effects properly

`godmode` - Prevents you from taking damage

`resetconst` - Resets the constellation level on your current active character, will need to relog after using the command to see any changes.

`setstats [stats] [amount]` - Changes the current character's specified stat.

`clearartifacts` - Deletes all unequipped and unlocked level 0 artifacts, **including yellow rarity ones** from your inventory

`pos` - Gets your current coordinate.

`weather [weather id] [climate id]` - Changes the current weather.

*More commands will be updated in the [wiki](https://github.com/Melledy/Grasscutter/wiki/).*

### Bonus
When you want to teleport to somewhere, use the ingame marking function on Map, click Confirm. You will see your character falling from a very high destination, exact location that you marked.

# Quick Troubleshooting
* If compiling wasn't successful, please check your JDK installation (must be JDK 8 and validated JDK's bin PATH variable)
* My client doesn't connect, doesn't login, 4206, etc... - Mostly your proxy daemon setup is *the issue*, if using Fiddler make sure it running on another port except 8888
* Startup sequence: Mongodb > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Client
