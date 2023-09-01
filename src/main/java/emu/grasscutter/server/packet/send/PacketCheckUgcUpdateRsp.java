package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CheckUgcUpdateRspOuterClass.CheckUgcUpdateRsp;
import emu.grasscutter.net.proto.UgcTypeOuterClass.UgcType;

public class PacketCheckUgcUpdateRsp extends BasePacket {

    public PacketCheckUgcUpdateRsp(UgcType ugcType) {
        super(PacketOpcodes.CheckUgcUpdateRsp);

        var proto = CheckUgcUpdateRsp.newBuilder();

        proto.setUgcType(ugcType);

        this.setData(proto);
    }
}
