package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterSceneDoneRspOuterClass.EnterSceneDoneRsp;

public class PacketEnterSceneDoneRsp extends GenshinPacket {

	public PacketEnterSceneDoneRsp(GenshinPlayer player) {
		super(PacketOpcodes.EnterSceneDoneRsp);

		EnterSceneDoneRsp p = EnterSceneDoneRsp.newBuilder()
				.setEnterSceneToken(player.getEnterSceneToken())
				.build();
		
		this.setData(p);
	}
}
