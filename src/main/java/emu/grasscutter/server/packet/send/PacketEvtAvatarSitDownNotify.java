package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtAvatarSitDownNotifyOuterClass.EvtAvatarSitDownNotify;

public class PacketEvtAvatarSitDownNotify extends BasePacket {

    public PacketEvtAvatarSitDownNotify(EvtAvatarSitDownNotify notify) {
        super(PacketOpcodes.EvtAvatarSitDownNotify);

        EvtAvatarSitDownNotify proto = EvtAvatarSitDownNotify.newBuilder()
                .setEntityId(notify.getEntityId())
                .setPosition(notify.getPosition())
                .setChairId(notify.getChairId())
                .build();

        this.setData(proto);
    }
}
