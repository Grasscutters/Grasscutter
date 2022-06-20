package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;

public abstract class ChallengeTrigger {
    public void onBegin(WorldChallenge challenge) {
    }

    public void onFinish(WorldChallenge challenge) {
    }

    public void onMonsterDeath(WorldChallenge challenge, EntityMonster monster) {
    }

    public void onGadgetDeath(WorldChallenge challenge, EntityGadget gadget) {
    }

    public void onCheckTimeout(WorldChallenge challenge) {
    }

    public void onGadgetDamage(WorldChallenge challenge, EntityGadget gadget) {
    }
}
