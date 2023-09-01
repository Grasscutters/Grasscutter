package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.WorldChestOpenNotifyOuterClass.WorldChestOpenNotify;

public class PacketWorldChestOpenNotify extends BasePacket {
    public PacketWorldChestOpenNotify(int groupId, int sceneId, int configId) {
        super(PacketOpcodes.WorldChestOpenNotify);

        this.setData(
                WorldChestOpenNotify.newBuilder()
                        .setGroupId(groupId)
                        .setSceneId(sceneId)
                        .setConfigId(configId)
                        .build());
    }
}
