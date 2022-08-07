package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ScenePointUnlockNotifyOuterClass.ScenePointUnlockNotify;

public class PacketScenePointUnlockNotify extends BasePacket {
	public PacketScenePointUnlockNotify(int sceneId, int pointId) {
		super(PacketOpcodes.ScenePointUnlockNotify);

		ScenePointUnlockNotify.Builder p = ScenePointUnlockNotify.newBuilder()
				.setSceneId(sceneId)
				.addPointList(pointId);

		this.setData(p);
	}

	public PacketScenePointUnlockNotify(int sceneId, List<Integer> pointIds) {
		super(PacketOpcodes.ScenePointUnlockNotify);

		ScenePointUnlockNotify.Builder p = ScenePointUnlockNotify.newBuilder()
				.setSceneId(sceneId)
				.addAllPointList(pointIds);

		this.setData(p);
	}
}
