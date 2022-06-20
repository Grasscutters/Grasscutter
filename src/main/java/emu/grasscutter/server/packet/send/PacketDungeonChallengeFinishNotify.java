package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonChallengeFinishNotifyOuterClass.DungeonChallengeFinishNotify;

public class PacketDungeonChallengeFinishNotify extends BasePacket {

    public PacketDungeonChallengeFinishNotify(WorldChallenge challenge) {
        super(PacketOpcodes.DungeonChallengeFinishNotify, true);

        DungeonChallengeFinishNotify proto = DungeonChallengeFinishNotify.newBuilder()
            .setChallengeIndex(challenge.getChallengeIndex())
            .setIsSuccess(challenge.isSuccess())
            .setChallengeRecordType(2)
            .build();

        this.setData(proto);
    }
}
