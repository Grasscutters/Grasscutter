package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtAvatarSitDownNotifyOuterClass.EvtAvatarSitDownNotify;

public class PacketEvtAvatarSitDownNotify extends GenshinPacket {

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
