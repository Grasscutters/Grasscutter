# Grasscutter
A WIP server emulator for Genshin Impact 2.3-2.6

# Current features
* Logging in
* Spawning monsters via console
* Combat
* Inventory features (recieving items/characters, upgrading items/characters, etc)
* Co-op does work, but movement is kind of buggy and some player ults do not spawn properly
* Friends list
* Gacha system

# Running the server and client

### Prerequisites
* Java 8 JDK
* Mongodb (recommended 4.0+)

### Starting up the server
1. Compile the server with `./gradlew jar`
2. Create a folder named `resources` in your server directory, you will need to copy `BinData` and `ExcelBinOutput` folders which you can get from a repo like [https://github.com/Dimbreath/GenshinData](https://github.com/Dimbreath/GenshinData) into your resources folder.
3. Run the server with `java -jar grasscutter.jar`. Make sure mongodb is running as well.

### Connecting with the client
1. If you are using the provided keystore, you will need to install and have [Fiddler](https://www.telerik.com/fiddler) running. Make sure fiddler is set to decrypt https traffic.
2. Set your hosts file to redirect at least `api-account-os.hoyoverse.com` and `dispatchosglobal.yuanshen.com` to your dispatch server ip.

### Server console commands

`/account create [username] {playerid}` - Creates an account with the specified username and the in-game uid for that account. The playerid parameter is optional and will be auto generated if not set.

### In-Game commands
There is a dummy user named "Server" in every player's friends list that you can message to use commands. Commands also work in other chat rooms, such as private/team chats.

`!spawn [monster id] [level] [amount]`

`!give [item id] [amount]`

`!drop [item id] [amount]`

`!killall`

`!godmode` - Prevents you from taking damage

`!resetconst` - Resets the constellation level on your current active character, will need to relog after using the command to see any changes.

`!sethp [hp]`

`!clearartifacts` - Deletes all unequipped and unlocked level 0 artifacts, **including yellow rarity ones** from your inventory
