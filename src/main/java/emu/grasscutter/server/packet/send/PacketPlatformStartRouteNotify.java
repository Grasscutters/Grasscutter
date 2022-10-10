package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketPlatformStartRouteNotify extends BasePacket {
    public PacketPlatformStartRouteNotify() {
        super(PacketOpcodes.PlatformStartRouteNotify);
    }
}
