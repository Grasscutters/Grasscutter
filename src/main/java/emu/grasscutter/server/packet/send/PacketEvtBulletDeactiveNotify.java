package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtBulletDeactiveNotifyOuterClass;

public class PacketEvtBulletDeactiveNotify extends BasePacket {
    public PacketEvtBulletDeactiveNotify(
            EvtBulletDeactiveNotifyOuterClass.EvtBulletDeactiveNotify notify) {
        super(PacketOpcodes.EvtBulletDeactiveNotify);

        this.setData(EvtBulletDeactiveNotifyOuterClass.EvtBulletDeactiveNotify.newBuilder(notify));
    }
}
