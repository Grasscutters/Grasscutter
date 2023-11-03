package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;

public class InTimeTrigger extends ChallengeTrigger {
    @Override
    public void onBegin(WorldChallenge challenge) {
        // Show time remaining UI
        var scene = challenge.getScene();
        scene.broadcastPacket(
                new PacketChallengeDataNotify(
                        challenge,
                        2,
                        // Compensate for time passed so far in scene.
                        challenge.getTimeLimit() + scene.getSceneTimeSeconds()));
    }

    @Override
    public void onCheckTimeout(WorldChallenge challenge) {
        var current = challenge.getScene().getSceneTimeSeconds();
        if (current - challenge.getStartedAt() > challenge.getTimeLimit()) {
            challenge.fail();
        }
    }
}
