package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EntityMoveInfoOuterClass.EntityMoveInfo;
import emu.grasscutter.net.proto.SceneEntityMoveNotifyOuterClass.SceneEntityMoveNotify;

public class PacketSceneEntityMoveNotify extends BasePacket {

    public PacketSceneEntityMoveNotify(EntityMoveInfo moveInfo) {
        super(PacketOpcodes.SceneEntityMoveNotify, true);

        SceneEntityMoveNotify proto =
                SceneEntityMoveNotify.newBuilder()
                        .setMotionInfo(moveInfo.getMotionInfo())
                        .setEntityId(moveInfo.getEntityId())
                        .setSceneTime(moveInfo.getSceneTime())
                        .setReliableSeq(moveInfo.getReliableSeq())
                        .build();

        this.setData(proto);
    }
}
