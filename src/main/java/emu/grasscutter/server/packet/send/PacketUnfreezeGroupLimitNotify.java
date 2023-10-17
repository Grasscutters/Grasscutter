package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.UnfreezeGroupLimitNotifyOuterClass.UnfreezeGroupLimitNotify;

public class PacketUnfreezeGroupLimitNotify extends BasePacket {
    public PacketUnfreezeGroupLimitNotify(int pointId, int sceneId) {
        super(PacketOpcodes.UnfreezeGroupLimitNotify);
        this.setData(UnfreezeGroupLimitNotify.newBuilder().setPointId(pointId).setSceneId(sceneId));
    }
}
