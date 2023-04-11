package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.dungeons.dungeon_results.BaseDungeonResult;

public interface DungeonSettleListener {
    void onDungeonSettle(DungeonManager dungeonManager, BaseDungeonResult.DungeonEndReason endReason);
}
