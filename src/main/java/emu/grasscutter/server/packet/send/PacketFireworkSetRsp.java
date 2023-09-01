package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ReformFireworksRspOuterClass.ReformFireworksRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketFireworkSetRsp extends BasePacket {
    public PacketFireworkSetRsp() {
        super(PacketOpcodes.ReformFireworksRsp);

        this.setData(ReformFireworksRsp.newBuilder().setRetcode(Retcode.RET_SUCC.getNumber()));
    }
}
