package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketFireworkSetRsp extends BasePacket {

    public PacketFireworkSetRsp() {
        super(PacketOpcodes.FireworkSetRsp);

    }

}
