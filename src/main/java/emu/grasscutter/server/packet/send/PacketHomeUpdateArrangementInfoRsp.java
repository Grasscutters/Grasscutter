package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketHomeUpdateArrangementInfoRsp extends BasePacket {

    public PacketHomeUpdateArrangementInfoRsp() {
        super(PacketOpcodes.HomeUpdateArrangementInfoRsp);
    }
}
