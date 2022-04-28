package emu.grasscutter.server.packet.send;

import java.util.Collection;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneEntityAppearNotifyOuterClass.SceneEntityAppearNotify;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;

public class PacketSceneEntityAppearNotify extends GenshinPacket {
	
	public PacketSceneEntityAppearNotify(GameEntity entity) {
		super(PacketOpcodes.SceneEntityAppearNotify, true);

		SceneEntityAppearNotify.Builder proto = SceneEntityAppearNotify.newBuilder()
				.setAppearType(VisionType.VISION_BORN)
				.addEntityList(entity.toProto());

		this.setData(proto.build());
	}
	
	public PacketSceneEntityAppearNotify(GameEntity entity, VisionType vision, int param) {
		super(PacketOpcodes.SceneEntityAppearNotify, true);

		SceneEntityAppearNotify.Builder proto = SceneEntityAppearNotify.newBuilder()
				.setAppearType(vision)
				.setParam(param)
				.addEntityList(entity.toProto());

		this.setData(proto.build());
	}
	
	public PacketSceneEntityAppearNotify(GenshinPlayer player) {
		this(player.getTeamManager().getCurrentAvatarEntity());
	}

	public PacketSceneEntityAppearNotify(Collection<GameEntity> entities, VisionType visionType) {
		super(PacketOpcodes.SceneEntityAppearNotify, true);
		
		SceneEntityAppearNotify.Builder proto = SceneEntityAppearNotify.newBuilder()
				.setAppearType(visionType);
		
		entities.forEach(e -> proto.addEntityList(e.toProto()));

		this.setData(proto.build());
	}
}
