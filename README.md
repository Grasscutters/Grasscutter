# Grasscutter
A WIP server emulator for Genshin Impact 2.3-2.6

**Note**: For support please join the [Discord server](https://discord.gg/T5vZU6UyeG).

# Current features
* Logging in
* Spawning monsters via console
* Combat
* Inventory features (recieving items/characters, upgrading items/characters, etc)
* Co-op does work, but movement is kind of buggy and some player ults do not spawn properly
* Friends list
* Gacha system

# Quick setup guide
* For more information, we now have [Grasscutter Wiki](https://github.com/Melledy/Grasscutter/wiki/) page !
### Prerequisites
* JDK-8u202 ([mirror link](https://mirrors.huaweicloud.com/java/jdk/8u202-b08/) since Oracle required an account to download old builds)
* Mongodb (recommended 4.0+)
* Proxy daemon: mitmproxy (mitmdump, recommended), Fiddler Classic, etc.

### Starting up Grasscutter server (Assuming you are on Windows)
1. Setup compile environment `gradlew.bat`
2. Compile Grasscutter with `gradlew jar`
3. Create a folder named `resources` in your Grasscutter directory, bring your `BinOutput` and `ExcelBinOutput` folders into it *(Check the wiki for more details where to get those.)*
4. Run Grasscutter with `java -jar grasscutter.jar`. Make sure mongodb is running as well.

### Connecting with the client
½. Create an account using command below
1. Run a proxy daemon: (choose either one)
	- mitmdump: `mitmdump -s proxy.py --ssl-insecure`
	- Fiddler Classic: Run Fiddler Classic, turn on `Decrypt https traffic` in setting and change the default port there (Tools -> Options -> Connections) to anything other than `8888`, and load [this script](https://github.lunatic.moe/fiddlerscript).
	- [Hosts file](https://github.com/Melledy/Grasscutter/wiki/Running#traffic-route-map)
2. Trust CA certificate:
	- mitmdump: `certutil -addstore root %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer`
2. Set network proxy to `127.0.0.1:8080` or the proxy port you specified.
4. *yoink*

* or you can use `run.cmd` to start Server & Proxy daemon with one click

# Grasscutter commands

A full list of commands, both for servers and for users, [can be found here](COMMANDS.md)
# Quick Troubleshooting
* If compiling wasnt successful, please check your JDK installation (must be JDK 8 and validated JDK's bin PATH variable)
* My client doesn't connect, doesn't login, 4206, etc... - Mostly your proxy daemon setup is the issue, if using Fiddler make sure it running on another port except 8888
* Startup sequence: Mongodb > Grasscutter > Proxy daemon (mitmdump, fiddler, etc.) > Client
* If `4206` error constantly prompt up, try to use [jdk-8u202-b08](https://mirrors.huaweicloud.com/java/jdk/8u202-b08/) instead of other versions of JDK
