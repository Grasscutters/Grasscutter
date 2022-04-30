package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChallengeDataNotifyOuterClass.ChallengeDataNotify;

public class PacketChallengeDataNotify extends BasePacket {
	
	public PacketChallengeDataNotify(DungeonChallenge challenge, int index, int value) {
		super(PacketOpcodes.ChallengeDataNotify);

		ChallengeDataNotify proto = ChallengeDataNotify.newBuilder()
				.setChallengeIndex(challenge.getChallengeIndex())
				.setParamIndex(index)
				.setValue(value)
				.build();
		
		this.setData(proto);
	}
}
