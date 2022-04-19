package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerApplyEnterMpReasonOuterClass.PlayerApplyEnterMpReason;
import emu.grasscutter.net.proto.PlayerApplyEnterMpResultNotifyOuterClass.PlayerApplyEnterMpResultNotify;

public class PacketPlayerApplyEnterMpResultNotify extends GenshinPacket {
	
	public PacketPlayerApplyEnterMpResultNotify(GenshinPlayer target, boolean isAgreed, PlayerApplyEnterMpReason reason) {
		super(PacketOpcodes.PlayerApplyEnterMpResultNotify);

		PlayerApplyEnterMpResultNotify proto = PlayerApplyEnterMpResultNotify.newBuilder()
				.setTargetUid(target.getUid())
				.setTargetNickname(target.getNickname())
				.setIsAgreed(isAgreed)
				.setReason(reason)
				.build();
		
		this.setData(proto);
	}
	
	public PacketPlayerApplyEnterMpResultNotify(int targetId, String targetName, boolean isAgreed, PlayerApplyEnterMpReason reason) {
		super(PacketOpcodes.PlayerApplyEnterMpResultNotify);

		PlayerApplyEnterMpResultNotify proto = PlayerApplyEnterMpResultNotify.newBuilder()
				.setTargetUid(targetId)
				.setTargetNickname(targetName)
				.setIsAgreed(isAgreed)
				.setReason(reason)
				.build();
		
		this.setData(proto);
	}
}
