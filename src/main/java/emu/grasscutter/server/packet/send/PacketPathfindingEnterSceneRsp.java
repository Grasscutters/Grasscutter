package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketPathfindingEnterSceneRsp extends GenshinPacket {

	public PacketPathfindingEnterSceneRsp(int clientSequence) {
		super(PacketOpcodes.PathfindingEnterSceneRsp);
		
		this.buildHeader(clientSequence);
	}
}
