package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonSettleNotifyOuterClass.DungeonSettleNotify;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.TowerLevelEndNotifyOuterClass.TowerLevelEndNotify;

public class PacketDungeonSettleNotify extends BasePacket {

    public PacketDungeonSettleNotify(WorldChallenge challenge) {
        super(PacketOpcodes.DungeonSettleNotify);

        DungeonSettleNotify proto = DungeonSettleNotify.newBuilder()
            .setDungeonId(challenge.getScene().getDungeonData().getId())
            .setIsSuccess(challenge.isSuccess())
            .setCloseTime(challenge.getScene().getAutoCloseTime())
            .setResult(challenge.isSuccess() ? 1 : 0)
            .build();

        this.setData(proto);
    }

    public PacketDungeonSettleNotify(WorldChallenge challenge,
                                     boolean canJump,
                                     boolean hasNextLevel,
                                     int nextFloorId
    ) {
        super(PacketOpcodes.DungeonSettleNotify);

        var continueStatus = TowerLevelEndNotify.ContinueStateType.CONTINUE_STATE_TYPE_CAN_NOT_CONTINUE_VALUE;
        if (challenge.isSuccess() && canJump) {
            continueStatus = hasNextLevel ? TowerLevelEndNotify.ContinueStateType.CONTINUE_STATE_TYPE_CAN_ENTER_NEXT_LEVEL_VALUE
                : TowerLevelEndNotify.ContinueStateType.CONTINUE_STATE_TYPE_CAN_ENTER_NEXT_FLOOR_VALUE;
        }

        var towerLevelEndNotify = TowerLevelEndNotify.newBuilder()
            .setIsSuccess(challenge.isSuccess())
            .setContinueState(continueStatus)
            .addFinishedStarCondList(1)
            .addFinishedStarCondList(2)
            .addFinishedStarCondList(3)
            .addRewardItemList(ItemParamOuterClass.ItemParam.newBuilder()
                .setItemId(201)
                .setCount(1000)
                .build());
        if (nextFloorId > 0 && canJump) {
            towerLevelEndNotify.setNextFloorId(nextFloorId);
        }

        DungeonSettleNotify proto = DungeonSettleNotify.newBuilder()
            .setDungeonId(challenge.getScene().getDungeonData().getId())
            .setIsSuccess(challenge.isSuccess())
            .setCloseTime(challenge.getScene().getAutoCloseTime())
            .setResult(challenge.isSuccess() ? 1 : 0)
            .setTowerLevelEndNotify(towerLevelEndNotify.build())
            .build();

        this.setData(proto);
    }
}
