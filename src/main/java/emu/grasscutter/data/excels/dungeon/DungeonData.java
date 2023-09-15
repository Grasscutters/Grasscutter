package emu.grasscutter.data.excels.dungeon;

import emu.grasscutter.data.*;
import emu.grasscutter.data.excels.RewardPreviewData;
import emu.grasscutter.game.dungeons.enums.*;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.scripts.data.SceneMeta;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "DungeonExcelConfigData.json")
public class DungeonData extends GameResource {

    @Getter(onMethod_ = @Override)
    private int id;

    @Getter private int sceneId;
    @Getter private int showLevel;
    private DungeonType type;
    private DungeonSubType subType;
    private DungeonPlayType playType;
    private DungeonInvolveType involveType;
    @Getter private int limitLevel;
    @Getter private int passCond;
    @Getter private int passJumpDungeon;
    @Getter private int reviveMaxCount;
    @Getter private int settleCountdownTime;
    @Getter private int failSettleCountdownTime;
    @Getter private int quitSettleCountdownTime;
    @Getter private List<SettleShowType> settleShows;
    @Getter private int passRewardPreviewID;
    @Getter private int statueCostID;
    @Getter private int statueCostCount;
    @Getter private int statueDrop;

    // not part of DungeonExcelConfigData
    @Getter private RewardPreviewData rewardPreviewData;

    public DungeonType getType() {
        if (type == null) {
            return DungeonType.DUNGEON_NONE;
        }
        return type;
    }

    public DungeonSubType getSubType() {
        if (subType == null) {
            return DungeonSubType.DUNGEON_SUB_NONE;
        }
        return subType;
    }

    public DungeonPlayType getPlayType() {
        if (playType == null) {
            return DungeonPlayType.DUNGEON_PLAY_TYPE_NONE;
        }
        return playType;
    }

    public DungeonInvolveType getInvolveType() {
        if (involveType == null) {
            return DungeonInvolveType.INVOLVE_NONE;
        }
        return involveType;
    }

    /**
     * @return The position of the player when they enter the dungeon.
     */
    public Position getStartPosition() {
        return SceneMeta.of(this.getSceneId()).config.born_pos;
    }

    /**
     * @return The rotation of the player when they enter the dungeon.
     */
    public Position getStartRotation() {
        return SceneMeta.of(this.getSceneId()).config.born_rot;
    }

    @Override
    public void onLoad() {
        if (this.passRewardPreviewID > 0) {
            this.rewardPreviewData = GameData.getRewardPreviewDataMap().get(this.passRewardPreviewID);
        }
    }
}
