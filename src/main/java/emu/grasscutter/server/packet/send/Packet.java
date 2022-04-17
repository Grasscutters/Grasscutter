package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class Packet extends GenshinPacket {
	
	public Packet() {
		super(PacketOpcodes.NONE);

	}
}
