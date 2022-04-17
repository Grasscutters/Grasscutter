package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketPlayerSetPauseRsp extends GenshinPacket {

	public PacketPlayerSetPauseRsp(int clientSequence) {
		super(PacketOpcodes.PlayerSetPauseRsp);
		
		this.buildHeader(clientSequence);
	}
}
