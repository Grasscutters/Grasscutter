package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerCookArgsRspOuterClass.PlayerCookArgsRsp;

public class PacketPlayerCookArgsRsp extends BasePacket {
    
    public PacketPlayerCookArgsRsp() {
        super(PacketOpcodes.PlayerCookArgsRsp);

        PlayerCookArgsRsp proto = PlayerCookArgsRsp.newBuilder()
            .build();

        this.setData(proto);
    }
}
