package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorldPlayerReviveRspOuterClass.WorldPlayerReviveRsp;

public class PacketWorldPlayerReviveRsp extends BasePacket {

    public PacketWorldPlayerReviveRsp() {
        super(PacketOpcodes.WorldPlayerReviveRsp);

        WorldPlayerReviveRsp.Builder proto = WorldPlayerReviveRsp.newBuilder();

        this.setData(proto.build());
    }
}
