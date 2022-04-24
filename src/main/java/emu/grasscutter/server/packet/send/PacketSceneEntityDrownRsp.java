package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;

public class PacketSceneEntityDrownRsp extends GenshinPacket {

    public PacketSceneEntityDrownRsp(int entityId) {
        super(PacketOpcodes.SceneEntityDrownRsp);

        SceneEntityDrownRsp proto = SceneEntityDrownRsp.newBuilder().setEntityId(entityId).build();

        this.setData(proto);
    }
}


