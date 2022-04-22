package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.custom.ScenePointEntry;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneTransToPointRspOuterClass.SceneTransToPointRsp;
import emu.grasscutter.utils.Position;

public class PacketSceneTransToPointRsp extends GenshinPacket {
	
	public PacketSceneTransToPointRsp(GenshinPlayer player, int pointId, int sceneId) {
		super(PacketOpcodes.SceneTransToPointRsp);

		SceneTransToPointRsp proto = SceneTransToPointRsp.newBuilder()
				.setRetcode(0)
	            .setPointId(pointId)
	            .setSceneId(sceneId)
	            .build();

		this.setData(proto);
	}

	public PacketSceneTransToPointRsp() {
		super(PacketOpcodes.SceneTransToPointRsp);
		
		SceneTransToPointRsp proto = SceneTransToPointRsp.newBuilder()
				.setRetcode(1) // Internal server error
	            .build();

		this.setData(proto);
	}
}
