package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;

public class KillMonsterCountTrigger extends ChallengeTrigger {
    @Override
    public void onBegin(WorldChallenge challenge) {
        challenge
                .getScene()
                .broadcastPacket(new PacketChallengeDataNotify(challenge, 1, challenge.getScore().get()));
    }

    @Override
    public void onMonsterDeath(WorldChallenge challenge, EntityMonster monster) {
        var newScore = challenge.increaseScore();
        challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 1, newScore));

        if (newScore >= challenge.getGoal()) {
            challenge.done();
        }
    }
}
