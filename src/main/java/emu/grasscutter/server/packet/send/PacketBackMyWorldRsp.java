package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BackMyWorldRspOuterClass;

public class PacketBackMyWorldRsp extends BasePacket {

    public PacketBackMyWorldRsp(int retcode) {
        super(PacketOpcodes.BackMyWorldRsp);

        var proto = BackMyWorldRspOuterClass.BackMyWorldRsp.newBuilder().setRetcode(retcode);

        this.setData(proto.build());
    }
}
