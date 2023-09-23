package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.StartCoopPointRspOuterClass;

public class PacketStartCoopPointRsp extends BasePacket {

    public PacketStartCoopPointRsp(int coopPoint) {
        super(PacketOpcodes.StartCoopPointRsp);

        StartCoopPointRspOuterClass.StartCoopPointRsp proto =
                StartCoopPointRspOuterClass.StartCoopPointRsp.newBuilder().setCoopPoint(coopPoint).build();

        this.setData(proto);
    }
}
