package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonChallengeBeginNotifyOuterClass.DungeonChallengeBeginNotify;

public class PacketDungeonChallengeBeginNotify extends BasePacket {

    public PacketDungeonChallengeBeginNotify(WorldChallenge challenge) {
        super(PacketOpcodes.DungeonChallengeBeginNotify, true);

        DungeonChallengeBeginNotify proto = DungeonChallengeBeginNotify.newBuilder()
            .setChallengeId(challenge.getChallengeId())
            .setChallengeIndex(challenge.getChallengeIndex())
            .setGroupId(challenge.getGroup().id)
            .addAllParamList(challenge.getParamList())
            .build();

        this.setData(proto);
    }
}
