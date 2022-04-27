package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class Packet extends BasePacket {
	
	public Packet() {
		super(PacketOpcodes.NONE);

	}
}
