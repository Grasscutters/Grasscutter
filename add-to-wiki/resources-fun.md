# Fun
## Prerequisites
- [MongoDBCompass](https://www.mongodb.com/try/download/compass)

***

### Colored nickname and signature :peacock: ([written by](https://github.com/actuallyeunha))
Unity supports colored text by default (See [Unity Manual](https://docs.unity3d.com/Packages/com.unity.ugui@1.0/manual/StyledText.html) for more info)

By replacing them with `<color=color>text</color>` or `<color=#HEXCODE>text</color>` you can change most, if not all, strings' colors.


1. Open MongoDBCompass and connect to your db
2. Go to `grasscutter/players`
3. Make your changes. *Change it inside `playerProfile` too!* <br>
        e.g  `nickname: "<color=#ff9ec6>na.na</color>"`; <br>
        `signature: "Running on <color=green>Grasscutter</color>!"`
4. Update the document
5. Relog to see changes

Enjoy your colorful name :sparkles:


***
## Avatar/Character
This guide is recommended if you have the character/weapon. If you want to get a character at Level Z, then use !givechar <avatarID> [level]
### Changing Level/Ascension/Talents
> Make sure you have created an account with the avatars/characters.
1. Open **MongoDBCompass**
2. Connect to the host (default URI is `mongodb://localhost:27017`)
3. Navigate to Databases > `grasscutter` > `avatars`
4. (Optional) Set **View** to `JSON View`
5. In the **Filter** field, type `{ avatarId: X }` where `X` is the Avatar ID that you are trying to modify. **Avatar IDs are 8-digits.**
6. Click the **Find** next to the field. 
7. After the documents have been filtered, edit the document
	1. To change character **level**, change the value next to `"level"`
	2. To change character **ascension**, change the value next to `"promoteLevel"`
		 Check [Wiki](https://genshin-impact.fandom.com/wiki/Characters#:~:text=one%20Acquaint%20Fate.-,Ascension%20Phase,-Max%20Char.%20Level) for ascension values
	3. To change **talents**, expand `"proudSkillList"`
		1. To unlock the avatar's **1st Ascension Passive**, add XX2101 inside `"proudSkillList"`, where **XX is the last 2 digits of the Avatar ID**. **Make sure to add a `,` for each line.** 
		2. To unlock the avatar's **4th Ascension Passive**, do the same as before, but replace `2101` with `2201`.
8. After editing, you can now **Replace**.

### Example
A document in JSON view that has the Avatar ID 10000058, level 90, ascension phase 6, all talents unlocked.

<a href="https://imgur.com/ZJf6L3K"><img src="https://i.imgur.com/ZJf6L3K.png" title="source: imgur.com" /></a>


### Notes
- On the last line of `"proudSkillList"`, a `,` is no longer needed. 
- After replacing the document, make sure to restart the server for changes to take effect. 


## Weapons
> Make sure you have created an account with the weapons.
1. Open **MongoDBCompass**
2. Connect to the host (default URI is `mongodb://localhost:27017`)
3. Navigate to Databases > `grasscutter` > `items`
4. (Optional) Set **View** to `JSON View`
5. In the **Filter** field, type `{ itemId: Y }` where `Y` is the Item/Weapon ID that you are trying to modify. **Weapon IDs are 5 digits.**
6. Click the **Find** next to the field. 
7. After the documents have been filtered, edit the document
	1. To change weapon **level**, change the value next to `"level"`
	2. To change weapon **ascension**, change the value next to `"promoteLevel"`
		 Check [Wiki](https://genshin-impact.fandom.com/wiki/Weapons#:~:text=reaching%202nd%20Ascension.-,Ascension%20Phase,-Max%20Weapon%20Level) for ascension values
	3. To change **refinement**, change the value next to `"refinement"`
		* Refinement Rank 1 = 0
		* Refinement Rank 2 = 1
		* Refinement Rank 3 = 2
		* Refinement Rank 4 = 3
		* Refinement Rank 5 = 4
8. After editing, you can now **Replace**.

### Example
A document in JSON view that has the Weapon ID 12503, level 90, ascension phase 6, refinement rank 5.

<a href="https://imgur.com/b8yW79b"><img src="https://i.imgur.com/b8yW79b.png" title="source: imgur.com" /></a>
