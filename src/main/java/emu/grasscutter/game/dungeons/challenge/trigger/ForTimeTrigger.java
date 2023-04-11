package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;

public class ForTimeTrigger extends ChallengeTrigger {
    @Override
    public void onCheckTimeout(WorldChallenge challenge) {
        var current = challenge.getScene().getSceneTimeSeconds();
        if (current - challenge.getStartedAt() > challenge.getTimeLimit()) {
            challenge.done();
        }
    }
}
