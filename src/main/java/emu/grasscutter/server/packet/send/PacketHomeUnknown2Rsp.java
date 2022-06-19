package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketHomeUnknown2Rsp extends BasePacket {

    public PacketHomeUnknown2Rsp() {
        super(PacketOpcodes.HomeUnknown2Rsp);

    }
}
