package emu.grasscutter.game.dungeons.dungeon_results;

import emu.grasscutter.data.excels.dungeon.DungeonData;
import emu.grasscutter.game.dungeons.DungeonEndStats;
import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.tower.TowerManager;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.net.proto.TowerLevelEndNotifyOuterClass.TowerLevelEndNotify;
import emu.grasscutter.net.proto.TowerLevelEndNotifyOuterClass.TowerLevelEndNotify.ContinueStateType;

public class TowerResult extends BaseDungeonResult {
    WorldChallenge challenge;
    boolean canJump;
    boolean hasNextLevel;
    int nextFloorId;

    public TowerResult(
            DungeonData dungeonData,
            DungeonEndStats dungeonStats,
            TowerManager towerManager,
            WorldChallenge challenge) {
        super(dungeonData, dungeonStats);
        this.challenge = challenge;
        this.canJump = towerManager.hasNextFloor();
        this.hasNextLevel = towerManager.hasNextLevel();
        this.nextFloorId = hasNextLevel ? 0 : towerManager.getNextFloorId();
    }

    @Override
    protected void onProto(DungeonSettleNotifyOuterClass.DungeonSettleNotify.Builder builder) {
        var continueStatus = ContinueStateType.CONTINUE_STATE_TYPE_CAN_NOT_CONTINUE_VALUE;
        if (challenge.isSuccess() && canJump) {
            continueStatus =
                    hasNextLevel
                            ? ContinueStateType.CONTINUE_STATE_TYPE_CAN_ENTER_NEXT_LEVEL_VALUE
                            : ContinueStateType.CONTINUE_STATE_TYPE_CAN_ENTER_NEXT_FLOOR_VALUE;
        }

        var towerLevelEndNotify =
                TowerLevelEndNotify.newBuilder()
                        .setIsSuccess(challenge.isSuccess())
                        .setContinueState(continueStatus)
                        .addFinishedStarCondList(1)
                        .addFinishedStarCondList(2)
                        .addFinishedStarCondList(3)
                        .addRewardItemList(
                                ItemParamOuterClass.ItemParam.newBuilder().setItemId(201).setCount(1000).build());
        if (nextFloorId > 0 && canJump) {
            towerLevelEndNotify.setNextFloorId(nextFloorId);
        }
        builder.setTowerLevelEndNotify(towerLevelEndNotify);
    }
}
