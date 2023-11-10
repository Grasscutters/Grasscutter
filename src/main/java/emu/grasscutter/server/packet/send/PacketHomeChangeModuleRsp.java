package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeModuleRspOuterClass.HomeChangeModuleRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketHomeChangeModuleRsp extends BasePacket {

    public PacketHomeChangeModuleRsp(int targetModuleId) {
        super(PacketOpcodes.HomeChangeModuleRsp);

        var proto =
                HomeChangeModuleRsp.newBuilder().setRetcode(0).setTargetModuleId(targetModuleId).build();

        this.setData(proto);
    }

    public PacketHomeChangeModuleRsp(Retcode retcode) {
        super(PacketOpcodes.HomeChangeModuleRsp);

        this.setData(HomeChangeModuleRsp.newBuilder().setRetcode(retcode.getNumber()));
    }
}
