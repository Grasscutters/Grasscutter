package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;

public class PacketSceneEntityDisappearNotify extends GenshinPacket {
	
	public PacketSceneEntityDisappearNotify(GenshinEntity entity, VisionType disappearType) {
		super(PacketOpcodes.SceneEntityDisappearNotify);

		SceneEntityDisappearNotify proto = SceneEntityDisappearNotify.newBuilder()
				.setDisappearType(disappearType)
				.addEntityList(entity.getId())
				.build();

		this.setData(proto);
	}
}
