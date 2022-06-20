package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeStartRspOuterClass.ForgeStartRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketForgeStartRsp extends BasePacket {

    public PacketForgeStartRsp(Retcode retcode) {
        super(PacketOpcodes.ForgeStartRsp);

        ForgeStartRsp proto = ForgeStartRsp.newBuilder()
            .setRetcode(retcode.getNumber())
            .build();

        this.setData(proto);
    }
}