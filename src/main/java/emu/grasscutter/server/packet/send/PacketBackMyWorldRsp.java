package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.BackMyWorldRspOuterClass;

public class PacketBackMyWorldRsp extends BasePacket {

    public PacketBackMyWorldRsp(int retcode) {
        super(PacketOpcodes.BackMyWorldRsp);

        var proto = BackMyWorldRspOuterClass.BackMyWorldRsp.newBuilder().setRetcode(retcode);

        this.setData(proto.build());
    }
}
