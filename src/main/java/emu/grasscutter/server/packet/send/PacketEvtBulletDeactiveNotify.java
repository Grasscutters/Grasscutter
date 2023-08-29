package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtBulletDeactiveNotifyOuterClass;

public class PacketEvtBulletDeactiveNotify extends BasePacket {
    public PacketEvtBulletDeactiveNotify(EvtBulletDeactiveNotifyOuterClass.EvtBulletDeactiveNotify notify) {
        super(PacketOpcodes.EvtBulletDeactiveNotify);

        this.setData(EvtBulletDeactiveNotifyOuterClass.EvtBulletDeactiveNotify.newBuilder(notify));
    }
}
