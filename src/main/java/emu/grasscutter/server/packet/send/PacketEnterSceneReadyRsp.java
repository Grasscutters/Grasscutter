package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterSceneReadyRspOuterClass.EnterSceneReadyRsp;

public class PacketEnterSceneReadyRsp extends BasePacket {

	public PacketEnterSceneReadyRsp(Player player) {
		super(PacketOpcodes.EnterSceneReadyRsp, 11);
		
		EnterSceneReadyRsp p = EnterSceneReadyRsp.newBuilder()
				.setEnterSceneToken(player.getEnterSceneToken())
				.build();
		
		this.setData(p.toByteArray());
	}
}
