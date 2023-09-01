package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ServerTimeNotifyOuterClass.ServerTimeNotify;

public class PacketServerTimeNotify extends BasePacket {

    public PacketServerTimeNotify() {
        super(PacketOpcodes.ServerTimeNotify);

        ServerTimeNotify proto =
                ServerTimeNotify.newBuilder().setServerTime(System.currentTimeMillis()).build();

        this.setData(proto);
    }
}
