package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PostEnterSceneRspOuterClass.PostEnterSceneRsp;

public class PacketPostEnterSceneRsp extends GenshinPacket {

	public PacketPostEnterSceneRsp(GenshinPlayer player) {
		super(PacketOpcodes.PostEnterSceneRsp);
		
		PostEnterSceneRsp p = PostEnterSceneRsp.newBuilder()
			.setEnterSceneToken(player.getEnterSceneToken())
			.build();
		
		this.setData(p);
	}
}
