package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeModuleRspOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketHomeChangeModuleRsp extends BasePacket {

    public PacketHomeChangeModuleRsp(int targetModuleId) {
        super(PacketOpcodes.HomeChangeModuleRsp);

        HomeChangeModuleRspOuterClass.HomeChangeModuleRsp proto =
            HomeChangeModuleRspOuterClass.HomeChangeModuleRsp.newBuilder()
                .setRetcode(0)
                .setTargetModuleId(targetModuleId)
                .build();

        this.setData(proto);
    }

    public PacketHomeChangeModuleRsp(RetcodeOuterClass.Retcode retcode) {
        super(PacketOpcodes.HomeChangeModuleRsp);

        this.setData(
            HomeChangeModuleRspOuterClass.HomeChangeModuleRsp.newBuilder()
                .setRetcode(retcode.getNumber()));
    }
}
