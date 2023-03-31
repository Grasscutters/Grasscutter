package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;

public class InTimeTrigger extends ChallengeTrigger {
    @Override
    public void onCheckTimeout(WorldChallenge challenge) {
        var current = System.currentTimeMillis();
        if (current - challenge.getStartedAt() > challenge.getTimeLimit() * 1000L) {
            challenge.fail();
        }
    }
}
