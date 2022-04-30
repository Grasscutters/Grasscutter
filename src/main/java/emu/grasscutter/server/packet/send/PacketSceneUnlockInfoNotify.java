package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneUnlockInfoNotifyOuterClass.SceneUnlockInfoNotify;
import emu.grasscutter.net.proto.SceneUnlockInfoOuterClass.SceneUnlockInfo;

public class PacketSceneUnlockInfoNotify extends BasePacket {
	
	public PacketSceneUnlockInfoNotify() {
		super(PacketOpcodes.SceneUnlockInfoNotify); // Rename opcode later

		SceneUnlockInfoNotify proto = SceneUnlockInfoNotify.newBuilder()
				.addUnlockInfos(SceneUnlockInfo.newBuilder().setSceneId(1))
				.addUnlockInfos(SceneUnlockInfo.newBuilder().setSceneId(3).addSceneTagIdList(102).addSceneTagIdList(113).addSceneTagIdList(117))
				.addUnlockInfos(SceneUnlockInfo.newBuilder().setSceneId(4).addSceneTagIdList(106).addSceneTagIdList(109))
				.addUnlockInfos(SceneUnlockInfo.newBuilder().setSceneId(5))
				.addUnlockInfos(SceneUnlockInfo.newBuilder().setSceneId(6))
				.addUnlockInfos(SceneUnlockInfo.newBuilder().setSceneId(7))
				.build();
		
		this.setData(proto);
	}
}
