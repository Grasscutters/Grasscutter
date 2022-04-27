package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneEntityDrownRspOuterClass.SceneEntityDrownRsp;

public class PacketSceneEntityDrownRsp extends GenshinPacket {

    public PacketSceneEntityDrownRsp(int entityId) {
        super(PacketOpcodes.SceneEntityDrownRsp);

        SceneEntityDrownRsp proto = SceneEntityDrownRsp.newBuilder().setEntityId(entityId).build();

        this.setData(proto);
    }
}


