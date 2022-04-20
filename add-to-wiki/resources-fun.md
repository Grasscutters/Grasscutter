# Fun
## Colored nickname and signature :peacock:
Unity supports colored text by default (See [Unity Manual](https://docs.unity3d.com/Packages/com.unity.ugui@1.0/manual/StyledText.html) for more info)

By replacing them with `<color=color>text</color>` or `<color=#HEXCODE>text</color>` you can change most, if not all, strings' colors.
 
**It's not possible to do this directly in game, you have to edit the db using something like MongoDBCompass!**

1. Open MongoDBCompass and connect to your db
2. Go to `grasscutter/players`
3. Make your changes. *Change it inside `playerProfile` too!* <br>
        e.g  `nickname: "<color=#ff9ec6>na.na</color>"`; <br>
        `signature: "Running on <color=green>Grasscutter</color>!"`
4. Update the document
5. Relog to see changes

Enjoy your colorful name :sparkles: