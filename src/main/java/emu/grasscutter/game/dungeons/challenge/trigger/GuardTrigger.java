package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;

public class GuardTrigger extends ChallengeTrigger {
    private final int entityToProtectCFGId;
    private int lastSendPercent = 100;

    public GuardTrigger(int entityToProtectCFGId) {
        this.entityToProtectCFGId = entityToProtectCFGId;
    }

    public void onBegin(WorldChallenge challenge) {
        challenge.setGuardEntity(
                challenge.getScene().getEntityByConfigId(entityToProtectCFGId, challenge.getGroup().id));
        lastSendPercent = challenge.getGuardEntityHpPercent();
        challenge
                .getScene()
                .broadcastPacket(new PacketChallengeDataNotify(challenge, 2, lastSendPercent));
    }

    @Override
    public void onGadgetDamage(WorldChallenge challenge, EntityGadget gadget) {
        if (gadget.getConfigId() != entityToProtectCFGId) {
            return;
        }
        var percent = challenge.getGuardEntityHpPercent();

        if (percent != lastSendPercent) {
            challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 2, percent));
            lastSendPercent = percent;
        }

        if (percent <= 0) {
            challenge.fail();
        }
    }
}
