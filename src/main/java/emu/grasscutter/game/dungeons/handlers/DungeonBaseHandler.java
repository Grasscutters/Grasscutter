package emu.grasscutter.game.dungeons.handlers;

import emu.grasscutter.data.excels.dungeon.DungeonPassConfigData;

public abstract class DungeonBaseHandler {

    public abstract boolean execute(
            DungeonPassConfigData.DungeonPassCondition condition, int... params);
}
