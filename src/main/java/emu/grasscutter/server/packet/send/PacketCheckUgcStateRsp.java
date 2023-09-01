package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CheckUgcStateRspOuterClass.CheckUgcStateRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketCheckUgcStateRsp extends BasePacket {

    public PacketCheckUgcStateRsp(RetcodeOuterClass.Retcode ret) {
        super(PacketOpcodes.CheckUgcStateRsp);

        this.setData(CheckUgcStateRsp.newBuilder().setRetcode(ret.getNumber()));
    }
}
