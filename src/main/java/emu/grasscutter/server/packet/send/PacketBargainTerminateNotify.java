package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BargainTerminateNotifyOuterClass.BargainTerminateNotify;

public final class PacketBargainTerminateNotify extends BasePacket {
    public PacketBargainTerminateNotify(int bargainId) {
        super(PacketOpcodes.BargainTerminateNotify);

        this.setData(BargainTerminateNotify.newBuilder().setBargainId(bargainId));
    }
}
