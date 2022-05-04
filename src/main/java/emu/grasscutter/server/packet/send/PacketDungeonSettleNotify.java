package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonSettleNotifyOuterClass.DungeonSettleNotify;

public class PacketDungeonSettleNotify extends BasePacket {
	
	public PacketDungeonSettleNotify(DungeonChallenge challenge) {
		super(PacketOpcodes.DungeonSettleNotify);

		DungeonSettleNotify proto = DungeonSettleNotify.newBuilder()
				.setDungeonId(challenge.getScene().getDungeonData().getId())
				.setIsSuccess(challenge.isSuccess())
				.setCloseTime(challenge.getScene().getAutoCloseTime())
				.setResult(challenge.isSuccess() ? 1 : 0)
				.build();
		
		this.setData(proto);
	}
}
