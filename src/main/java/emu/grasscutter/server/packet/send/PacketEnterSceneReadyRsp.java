package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterSceneReadyRspOuterClass.EnterSceneReadyRsp;

public class PacketEnterSceneReadyRsp extends GenshinPacket {

	public PacketEnterSceneReadyRsp(GenshinPlayer player) {
		super(PacketOpcodes.EnterSceneReadyRsp, 11);
		
		EnterSceneReadyRsp p = EnterSceneReadyRsp.newBuilder()
				.setEnterSceneToken(player.getEnterSceneToken())
				.build();
		
		this.setData(p.toByteArray());
	}
}
