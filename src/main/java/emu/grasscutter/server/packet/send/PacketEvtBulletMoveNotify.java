package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EvtBulletMoveNotifyOuterClass;

public class PacketEvtBulletMoveNotify extends BasePacket {
    public PacketEvtBulletMoveNotify(EvtBulletMoveNotifyOuterClass.EvtBulletMoveNotify notify) {
        super(PacketOpcodes.EvtBulletMoveNotify);

        this.setData(EvtBulletMoveNotifyOuterClass.EvtBulletMoveNotify.newBuilder(notify));
    }
}
