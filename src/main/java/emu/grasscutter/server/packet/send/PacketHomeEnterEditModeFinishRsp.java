package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketHomeEnterEditModeFinishRsp extends BasePacket {

    public PacketHomeEnterEditModeFinishRsp() {
        super(PacketOpcodes.HomeEnterEditModeFinishRsp);

    }
}
