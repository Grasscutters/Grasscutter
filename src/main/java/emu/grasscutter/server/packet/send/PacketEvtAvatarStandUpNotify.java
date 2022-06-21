package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtAvatarStandUpNotifyOuterClass.EvtAvatarStandUpNotify;

public class PacketEvtAvatarStandUpNotify extends BasePacket {

    public PacketEvtAvatarStandUpNotify(EvtAvatarStandUpNotify notify) {
        super(PacketOpcodes.EvtAvatarStandUpNotify);

        EvtAvatarStandUpNotify proto = EvtAvatarStandUpNotify.newBuilder()
            .setEntityId(notify.getEntityId())
            .setDirection(notify.getDirection())
            .setPerformId(notify.getPerformId())
            .setChairId(notify.getChairId())
            .build();

        this.setData(proto);
    }
}
