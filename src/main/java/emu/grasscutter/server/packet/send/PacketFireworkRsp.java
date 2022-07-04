package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketFireworkRsp extends BasePacket {

    public PacketFireworkRsp() {
        super(PacketOpcodes.FireworkRsp);
    }

}
