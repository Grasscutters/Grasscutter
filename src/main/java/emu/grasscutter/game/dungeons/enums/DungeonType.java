package emu.grasscutter.game.dungeons.enums;

import lombok.Getter;

public enum DungeonType {
    DUNGEON_NONE(false),
    DUNGEON_PLOT(true),
    DUNGEON_FIGHT(true),
    DUNGEON_DAILY_FIGHT(false),
    DUNGEON_WEEKLY_FIGHT(true),
    DUNGEON_DISCARDED(false),
    DUNGEON_TOWER(false),
    DUNGEON_BOSS(true),
    DUNGEON_ACTIVITY(false),
    DUNGEON_EFFIGY(false),
    DUNGEON_ELEMENT_CHALLENGE(true),
    DUNGEON_THEATRE_MECHANICUS(false),
    DUNGEON_FLEUR_FAIR(false),
    DUNGEON_CHANNELLER_SLAB_LOOP(false),
    DUNGEON_CHANNELLER_SLAB_ONE_OFF(false),
    DUNGEON_BLITZ_RUSH(true),
    DUNGEON_CHESS(false),
    DUNGEON_SUMO_COMBAT(false),
    DUNGEON_ROGUELIKE(false),
    DUNGEON_HACHI(false),
    DUNGEON_POTION(false),
    DUNGEON_MINI_ELDRITCH(false),
    DUNGEON_UGC(false),
    DUNGEON_GCG(false),
    DUNGEON_CRYSTAL_LINK(false),
    DUNGEON_IRODORI_CHESS(false),
    DUNGEON_ROGUE_DIARY(false),
    DUNGEON_DREAMLAND(false),
    DUNGEON_SUMMER_V2(true),
    DUNGEON_MUQADAS_POTION(false),
    DUNGEON_INSTABLE_SPRAY(false),
    DUNGEON_WIND_FIELD(false),
    DUNGEON_BIGWORLD_MIRROR(false),
    DUNGEON_FUNGUS_FIGHTER_TRAINING(false),
    DUNGEON_FUNGUS_FIGHTER_PLOT(false),
    DUNGEON_EFFIGY_CHALLENGE_V2(false),
    DUNGEON_CHAR_AMUSEMENT(false);

    @Getter private final boolean countsToBattlepass;

    DungeonType(boolean countsToBattlepass) {
        this.countsToBattlepass = countsToBattlepass;
    }
}
