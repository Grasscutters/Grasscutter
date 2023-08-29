package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EvtBulletHitNotifyOuterClass;

public class PacketEvtBulletHitNotify extends BasePacket {
    public PacketEvtBulletHitNotify(EvtBulletHitNotifyOuterClass.EvtBulletHitNotify notify) {
        super(PacketOpcodes.EvtBulletHitNotify);

        this.setData(EvtBulletHitNotifyOuterClass.EvtBulletHitNotify.newBuilder(notify));
    }
}
