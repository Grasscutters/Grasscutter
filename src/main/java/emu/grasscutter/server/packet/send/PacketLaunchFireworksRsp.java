package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;

public class PacketLaunchFireworksRsp extends BasePacket {

    public PacketLaunchFireworksRsp() {
        super(PacketOpcodes.LaunchFireworksRsp);
    }
}
