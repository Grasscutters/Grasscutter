package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetPlayerPropRspOuterClass.SetPlayerPropRsp;

public class PacketSetPlayerPropRsp extends BasePacket {

    public PacketSetPlayerPropRsp(int retCode) {
        super(PacketOpcodes.SetPlayerPropRsp);

        var proto = SetPlayerPropRsp.newBuilder();
        if (retCode != 0) {
            proto.setRetcode(retCode);
        }

        this.setData(proto.build());
    }
}
