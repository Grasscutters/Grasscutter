package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.dungeons.dungeon_results.BaseDungeonResult;
import lombok.Getter;

@Getter
public class DungeonEndStats {
    private final int killedMonsters;
    private final int timeTaken;
    private final int openChestCount;
    private final BaseDungeonResult.DungeonEndReason dungeonResult;

    public DungeonEndStats(
            int killedMonsters,
            int timeTaken,
            int openChestCount,
            BaseDungeonResult.DungeonEndReason dungeonResult) {
        this.killedMonsters = killedMonsters;
        this.timeTaken = timeTaken;
        this.dungeonResult = dungeonResult;
        this.openChestCount = openChestCount;
    }
}
