package emu.grasscutter.server.packet.send;

import java.util.Collection;
import java.util.List;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneEntityDisappearNotifyOuterClass.SceneEntityDisappearNotify;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;

public class PacketSceneEntityDisappearNotify extends BasePacket {
	
	public PacketSceneEntityDisappearNotify(GameEntity entity, VisionType disappearType) {
		super(PacketOpcodes.SceneEntityDisappearNotify);

		SceneEntityDisappearNotify proto = SceneEntityDisappearNotify.newBuilder()
				.setDisappearType(disappearType)
				.addEntityList(entity.getId())
				.build();

		this.setData(proto);
	}
	
	public PacketSceneEntityDisappearNotify(Collection<GameEntity> entities, VisionType disappearType) {
		super(PacketOpcodes.SceneEntityDisappearNotify);

		SceneEntityDisappearNotify.Builder proto = SceneEntityDisappearNotify.newBuilder()
				.setDisappearType(disappearType);
		
		entities.forEach(e -> proto.addEntityList(e.getId()));

		this.setData(proto);
	}
}
