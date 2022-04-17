package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneInitFinishRspOuterClass.SceneInitFinishRsp;

public class PacketSceneInitFinishRsp extends GenshinPacket {

	public PacketSceneInitFinishRsp(GenshinPlayer player) {
		super(PacketOpcodes.SceneInitFinishRsp, 11);
		
		SceneInitFinishRsp p = SceneInitFinishRsp.newBuilder().setEnterSceneToken(player.getEnterSceneToken()).build();
		
		this.setData(p.toByteArray());
	}
}
