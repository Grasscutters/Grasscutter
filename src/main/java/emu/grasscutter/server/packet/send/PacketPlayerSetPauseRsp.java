package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketPlayerSetPauseRsp extends BasePacket {

	public PacketPlayerSetPauseRsp(int clientSequence) {
		super(PacketOpcodes.PlayerSetPauseRsp);
		
		this.buildHeader(clientSequence);
	}
}
