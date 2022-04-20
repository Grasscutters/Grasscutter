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

		String code = sceneId + "_" + pointId;
		ScenePointEntry scenePointEntry = GenshinData.getScenePointEntries().get(code);

		float x = scenePointEntry.getPointData().getTranPos().getX();
		float y = scenePointEntry.getPointData().getTranPos().getY();
		float z = scenePointEntry.getPointData().getTranPos().getZ();

		player.getPos().set(new Position(x, y, z));

		player.getWorld().forceTransferPlayerToScene(player, sceneId, player.getPos());

		SceneTransToPointRsp proto = SceneTransToPointRsp.newBuilder()
                .setRetcode(0)
                .setPointId(pointId)
                .setSceneId(sceneId)
                .build();
		
		this.setData(proto);
	}
}
