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
        // In Tower challenges, time can run out without
        // causing the challenge to fail. (Player just
        // gets 0 stars when they ultimately finish.)
        var dungeonManager = challenge.getScene().getDungeonManager();
        if (dungeonManager != null && dungeonManager.isTowerDungeon()) return;

        var current = challenge.getScene().getSceneTimeSeconds();
        if (current - challenge.getStartedAt() > challenge.getTimeLimit()) {
            challenge.fail();
        }
    }
}
