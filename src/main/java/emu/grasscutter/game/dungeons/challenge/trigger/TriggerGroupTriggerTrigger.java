package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.scripts.data.SceneTrigger;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TriggerGroupTriggerTrigger extends ChallengeTrigger {
    String triggerTag;

    @Override
    public void onBegin(WorldChallenge challenge) {
        challenge
                .getScene()
                .broadcastPacket(new PacketChallengeDataNotify(challenge, 2, challenge.getScore().get()));
    }

    @Override
    public void onGroupTrigger(WorldChallenge challenge, SceneTrigger trigger) {
        if (!triggerTag.equals(trigger.getTag())) {
            return;
        }

        var newScore = challenge.increaseScore();
        challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 2, newScore));
        if (newScore >= challenge.getGoal()) {
            challenge.done();
        }
    }
}
