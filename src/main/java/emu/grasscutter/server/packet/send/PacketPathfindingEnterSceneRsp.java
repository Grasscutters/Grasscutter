package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;

public class PacketPathfindingEnterSceneRsp extends BasePacket {

	public PacketPathfindingEnterSceneRsp(int clientSequence) {
		super(PacketOpcodes.PathfindingEnterSceneRsp);
	}
}
