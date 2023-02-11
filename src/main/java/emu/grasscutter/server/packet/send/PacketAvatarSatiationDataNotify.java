package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.proto.AvatarSatiationDataNotifyOuterClass.AvatarSatiationDataNotify;
import emu.grasscutter.net.proto.AvatarSatiationDataOuterClass.AvatarSatiationData;

public class PacketAvatarSatiationDataNotify extends BasePacket {

	public PacketAvatarSatiationDataNotify(Avatar avatar, float curSatiation, float satiation) {
		super(PacketOpcodes.AvatarSatiationDataNotify);

		float total = ((satiation + curSatiation) / 0.3f);

		AvatarSatiationData.Builder avatarSatiation = AvatarSatiationData.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setFinishTime(total)
				.setPenaltyFinishTime(0);

		// Penalty for overeating - 30sec of no satiation decrease
		// Disable until graphic is consistently shown at correct status
		// if ((curSatiation + satiation) >= 100) {
		// avatarSatiation.setPenaltyFinishTime(30);
		// }

		AvatarSatiationData avatarSatiationData = avatarSatiation.build();

		AvatarSatiationDataNotify notify = AvatarSatiationDataNotify.newBuilder()
				.addSatiationDataList(avatarSatiationData)
				.build();

		this.setData(notify);
	}
}
