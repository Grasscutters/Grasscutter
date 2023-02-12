package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.proto.AvatarSatiationDataNotifyOuterClass.AvatarSatiationDataNotify;
import emu.grasscutter.net.proto.AvatarSatiationDataOuterClass.AvatarSatiationData;

public class PacketAvatarSatiationDataNotify extends BasePacket {

	public PacketAvatarSatiationDataNotify(Avatar avatar, float finishTime) {
		super(PacketOpcodes.AvatarSatiationDataNotify);

		AvatarSatiationData avatarSatiation = AvatarSatiationData.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setFinishTime(finishTime)
				.setPenaltyFinishTime(0.00f) // 0 for now, see below
				.build();

		// Penalty for overeating - 30sec of no satiation decrease
		// Disable until graphic is consistently shown at correct status
		// if ((curSatiation + satiation) >= 10000) {
		// avatarSatiation.setPenaltyFinishTime(3000);
		// }

		AvatarSatiationDataNotify notify = AvatarSatiationDataNotify.newBuilder()
				.addSatiationDataList(0, avatarSatiation)
				.build();

		this.setData(notify);
	}

	public PacketAvatarSatiationDataNotify(Avatar avatar) {
		super(PacketOpcodes.AvatarSatiationDataNotify);

		var avatarSatiation = AvatarSatiationData.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setFinishTime((avatar.getSatiation() / 30))
				.setPenaltyFinishTime(avatar.getSatiationPenalty())
				.build();

		AvatarSatiationDataNotify notify = AvatarSatiationDataNotify.newBuilder()
				.addSatiationDataList(avatarSatiation)
				.build();

		this.setData(notify);
	}
}
