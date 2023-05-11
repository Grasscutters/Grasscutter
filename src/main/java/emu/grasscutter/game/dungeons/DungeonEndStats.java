package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.dungeons.dungeon_results.BaseDungeonResult;
import lombok.Getter;

public class DungeonEndStats {
    @Getter private int killedMonsters;
    @Getter private int timeTaken;
    @Getter private int openChestCount;
    @Getter private BaseDungeonResult.DungeonEndReason dungeonResult;

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
