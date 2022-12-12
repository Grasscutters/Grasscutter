package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeBgmRspOuterClass;

public class PacketHomeChangeBgmRsp extends BasePacket {
    public PacketHomeChangeBgmRsp() {
        super(PacketOpcodes.HomeChangeBgmRsp);

        var rsp = HomeChangeBgmRspOuterClass.HomeChangeBgmRsp.newBuilder()
            .setRetcode(0)
            .build();

        this.setData(rsp);
    }
}
