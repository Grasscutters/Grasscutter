package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneAreaUnlockNotifyOuterClass.SceneAreaUnlockNotify;

public class PacketSceneAreaUnlockNotify extends BasePacket {
	public PacketSceneAreaUnlockNotify(int sceneId, int areaId) {
		super(PacketOpcodes.SceneAreaUnlockNotify);

		SceneAreaUnlockNotify.Builder p = SceneAreaUnlockNotify.newBuilder()
				.setSceneId(sceneId)
				.addAreaList(areaId);

		this.setData(p);
	}
}
