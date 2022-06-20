package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketHomeUpdateArrangementInfoRsp extends BasePacket {

    public PacketHomeUpdateArrangementInfoRsp() {
        super(PacketOpcodes.HomeUpdateArrangementInfoRsp);

    }
}
