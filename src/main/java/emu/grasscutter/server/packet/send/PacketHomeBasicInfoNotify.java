package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeBasicInfoNotifyOuterClass;
import emu.grasscutter.net.proto.HomeBasicInfoOuterClass;
import emu.grasscutter.net.proto.HomeLimitedShopInfoOuterClass;
import emu.grasscutter.net.proto.VectorOuterClass;

public class PacketHomeBasicInfoNotify extends BasePacket {

	public PacketHomeBasicInfoNotify(Player player, boolean editMode) {
		super(PacketOpcodes.HomeBasicInfoNotify);

		if(player.getCurrentRealmId() == null){
			return;
		}

		var proto = HomeBasicInfoNotifyOuterClass.HomeBasicInfoNotify.newBuilder();

		var sceneId = player.getCurrentRealmId() + 2000;
		var homeScene = player.getHome().getHomeSceneItem(sceneId);

		proto.setBasicInfo(HomeBasicInfoOuterClass.HomeBasicInfo.newBuilder()
				.setCurModuleId(player.getCurrentRealmId())
				.setCurRoomSceneId(homeScene.getRoomSceneId())
				.setIsInEditMode(editMode)
				.setHomeOwnerUid(player.getUid())
				.setLevel(1)
				.setOwnerNickName(player.getNickname())
				.setLimitedShopInfo(HomeLimitedShopInfoOuterClass.HomeLimitedShopInfo.newBuilder()
						.setDjinnPos(VectorOuterClass.Vector.newBuilder()
								.setZ(192)
								.setX(792)
								.setY(316.7f)
								.build())
						.setDjinnRot(VectorOuterClass.Vector.newBuilder()
								.setY(176)
								.build())
						.setNextCloseTime(Integer.MAX_VALUE)
						.setNextGuestOpenTime(0)
						.setNextOpenTime(0)
						.setUid(player.getUid())
						.build())
				.build());

		this.setData(proto);
	}
}
