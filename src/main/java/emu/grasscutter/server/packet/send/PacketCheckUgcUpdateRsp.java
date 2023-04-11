package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
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
