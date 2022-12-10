package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketLaunchFireworksRsp extends BasePacket {

    public PacketLaunchFireworksRsp() {
        super(PacketOpcodes.LaunchFireworksRsp);

    }

}
