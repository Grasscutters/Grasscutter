package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeModuleRspOuterClass;

public class PacketHomeChangeModuleRsp extends BasePacket {

    public PacketHomeChangeModuleRsp(int targetModuleId) {
        super(PacketOpcodes.HomeChangeModuleRsp);

        HomeChangeModuleRspOuterClass.HomeChangeModuleRsp proto = HomeChangeModuleRspOuterClass.HomeChangeModuleRsp.newBuilder()
                .setRetcode(0)
                .setTargetModuleId(targetModuleId)
                .build();

        this.setData(proto);
    }
}
