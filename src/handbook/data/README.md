# Handbook Data
Use Grasscutter's dumpers to generate the data to put here.

# Generating Data

When you have Grasscutter set up, you can use the following commands to generate the data:
- Commands - `grasscutter.jar -dump=commands,en-us`
- Items - `grasscutter.jar -dump=items,EN`
- Avatars - `grasscutter.jar -dump=avatars,EN`
- Quests - `grasscutter.jar -dump=quests,EN`
- Entities - `grasscutter.jar -dump=entities,en-us`
- Areas - `grasscutter.jar -dump=areas,EN`
- Scenes - `grasscutter.jar -dump=scenes,en-us`

Grasscutter being "set up" means:
- A Java runtime is installed
- Resources are provided in the working directory

## Language Locales

You can replace `en-us` or `EN` using the language locale which matches the format.

| Grasscutter Language Locale | Handbook Language Locale |
|-----------------------------|--------------------------|
| en-us                       | EN                       |


## Files Required
- `mainquests.csv`
- `commands.json`
- `entities.csv`
- `avatars.csv`
- `scenes.csv`
- `quests.csv`
- `items.csv`

# Item Icon Notes
- Artifacts: `https://bbs.hoyolab.com/hoyowiki/picture/reliquary/(name)/(piece)_icon.png`
  - Alternate source: `https://api.ambr.top/assets/UI/reliquary/UI_RelicIcon_(set)_(piece).png`
  - `xxxx4` - `flower_of_life`
  - `xxxx5` - `sands_of_eon`
  - `xxxx3` - `circlet_of_logos`/`plume_of_death`
    - Use `circlet_of_logos` with a complete set
    - Use `plume_of_death` with part of a set.
  - `xxxx2` - `plume_of_death`
  - `xxxx1` - `goblet_of_eonothem`
- Miscellaneous Items: `https://bbs.hoyolab.com/hoyowiki/picture/object/(name)_icon.png`
  - Includes: materials, quest items, food, etc.
  - Alternate source: `https://api.ambr.top/assets/UI/UI_ItemIcon_(id).png`
- Avatars/Avatar Items: `https://bbs.hoyolab.com/hoyowiki/picture/character/(name)_icon.png`
  - Avatar Items are between ranges `1001` and `1099`.
- Weapons: `https://api.ambr.top/assets/UI/UI_EquipIcon_(type)_(name).png`
- Furniture: `https://api.ambr.top/assets/UI/furniture/UI_Homeworld_(location)_(name).png`
- Monsters: `https://api.ambr.top/assets/UI/monster/UI_MonsterIcon_(type)_(variant).png`

# Credits
- [`...List.json` files](https://raw.githubusercontent.com/Dituon/grasscutter-command-helper/main/data/en-US) - Grasscutter Command Helper
- [Internal Asset API](https://ambr.top) - Project Amber
