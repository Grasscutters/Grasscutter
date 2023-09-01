package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeChooseModuleRspOuterClass;

public class PacketHomeChooseModuleRsp extends BasePacket {

    public PacketHomeChooseModuleRsp(int moduleId) {
        super(PacketOpcodes.HomeChooseModuleRsp);

        HomeChooseModuleRspOuterClass.HomeChooseModuleRsp proto =
                HomeChooseModuleRspOuterClass.HomeChooseModuleRsp.newBuilder()
                        .setRetcode(0)
                        .setModuleId(moduleId)
                        .build();

        this.setData(proto);
    }
}
