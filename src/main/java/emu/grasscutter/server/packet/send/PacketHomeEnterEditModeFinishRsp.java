package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketHomeEnterEditModeFinishRsp extends BasePacket {

    public PacketHomeEnterEditModeFinishRsp() {
        super(PacketOpcodes.HomeEnterEditModeFinishRsp);
    }
}
