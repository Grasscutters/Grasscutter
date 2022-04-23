package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.props.FetterState;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFetterDataNotifyOuterClass.AvatarFetterDataNotify;
import emu.grasscutter.net.proto.AvatarFetterInfoOuterClass.AvatarFetterInfo;
import emu.grasscutter.net.proto.FetterDataOuterClass.FetterData;

public class PacketAvatarFetterDataNotify extends GenshinPacket {
	
	public PacketAvatarFetterDataNotify(GenshinAvatar avatar) {
		super(PacketOpcodes.AvatarFetterDataNotify);

		AvatarFetterInfo.Builder avatarFetter = AvatarFetterInfo.newBuilder()
				.setExpLevel(avatar.getFetterLevel())
				.setExpNumber(avatar.getFetterExp());
		
		if (avatar.getFetterList() != null) {
			for (int i = 0; i < avatar.getFetterList().size(); i++) {
				avatarFetter.addFetterList(
					FetterData.newBuilder()
						.setFetterId(avatar.getFetterList().get(i))
						.setFetterState(FetterState.FINISH.getValue())
				);
			}
		}
		
		int rewardId = avatar.getNameCardRewardId();
		int cardId = avatar.getNameCardId();

		if (avatar.getPlayer().getNameCardList().contains(cardId)) {
			avatarFetter.addRewardedFetterLevelList(rewardId);
		}

		AvatarFetterInfo avatarFetterInfo = avatarFetter.build();
		
		AvatarFetterDataNotify proto = AvatarFetterDataNotify.newBuilder()
            .putFetterInfoMap(avatar.getGuid(), avatarFetterInfo)
            .build();
		
		this.setData(proto);
	}
}
