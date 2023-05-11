package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AiSyncInfoOuterClass.AiSyncInfo;
import emu.grasscutter.net.proto.EntityAiSyncNotifyOuterClass.EntityAiSyncNotify;

public class PacketEntityAiSyncNotify extends BasePacket {

    public PacketEntityAiSyncNotify(EntityAiSyncNotify notify) {
        super(PacketOpcodes.EntityAiSyncNotify, true);

        EntityAiSyncNotify.Builder proto = EntityAiSyncNotify.newBuilder();

        for (int monsterId : notify.getLocalAvatarAlertedMonsterListList()) {
            proto.addInfoList(AiSyncInfo.newBuilder().setEntityId(monsterId).setHasPathToTarget(true));
        }

        this.setData(proto);
    }
}
