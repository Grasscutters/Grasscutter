package emu.grasscutter.game.dungeons.handlers;

import emu.grasscutter.data.excels.DungeonPassConfigData;

public abstract class DungeonBaseHandler {

	public abstract boolean execute(DungeonPassConfigData.DungeonPassCondition condition, int... params);

}
