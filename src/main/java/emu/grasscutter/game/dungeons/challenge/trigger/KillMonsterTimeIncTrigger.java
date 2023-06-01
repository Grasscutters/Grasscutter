package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;

public class KillMonsterTimeIncTrigger extends ChallengeTrigger {

    private int increment;

    public KillMonsterTimeIncTrigger(int increment) {
        this.increment = increment;
    }

    @Override
    public void onBegin(WorldChallenge challenge) {
        // challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 0,
        // challenge.getScore().get()));
    }

    @Override
    public void onMonsterDeath(WorldChallenge challenge, EntityMonster monster) {
        challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 0, increment));

        challenge.setTimeLimit(challenge.getTimeLimit() + increment);
    }
}
