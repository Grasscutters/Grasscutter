package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonChallengeFinishNotifyOuterClass.DungeonChallengeFinishNotify;

public class PacketDungeonChallengeFinishNotify extends BasePacket {
	
	public PacketDungeonChallengeFinishNotify(DungeonChallenge challenge) {
		super(PacketOpcodes.DungeonChallengeFinishNotify);

		DungeonChallengeFinishNotify proto = DungeonChallengeFinishNotify.newBuilder()
				.setChallengeIndex(challenge.getChallengeIndex())
				.setIsSuccess(challenge.isSuccess())
				.setUnk1(challenge.getChallengeId())
				.setUnk2(30)
				.build();
		
		this.setData(proto);
	}
}
