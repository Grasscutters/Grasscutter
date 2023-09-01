package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeChangeBgmRspOuterClass;

public class PacketHomeChangeBgmRsp extends BasePacket {
    public PacketHomeChangeBgmRsp() {
        super(PacketOpcodes.HomeChangeBgmRsp);

        var rsp = HomeChangeBgmRspOuterClass.HomeChangeBgmRsp.newBuilder().setRetcode(0).build();

        this.setData(rsp);
    }
}
