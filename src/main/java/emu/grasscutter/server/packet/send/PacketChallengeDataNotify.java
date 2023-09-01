package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChallengeDataNotifyOuterClass.ChallengeDataNotify;

public class PacketChallengeDataNotify extends BasePacket {

    public PacketChallengeDataNotify(WorldChallenge challenge, int index, int value) {
        super(PacketOpcodes.ChallengeDataNotify);

        ChallengeDataNotify proto =
                ChallengeDataNotify.newBuilder()
                        .setChallengeIndex(challenge.getChallengeIndex())
                        .setParamIndex(index)
                        .setValue(value)
                        .build();

        this.setData(proto);
    }
}
