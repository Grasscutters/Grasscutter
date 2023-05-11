package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;

public class GuardTrigger extends ChallengeTrigger {
    private final int entityToProtectCFGId;
    private int lastSendPercent = 100;

    public GuardTrigger(int entityToProtectCFGId) {
        this.entityToProtectCFGId = entityToProtectCFGId;
    }

    public void onBegin(WorldChallenge challenge) {
        challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 2, 100));
    }

    @Override
    public void onGadgetDamage(WorldChallenge challenge, EntityGadget gadget) {
        if (gadget.getConfigId() != entityToProtectCFGId) {
            return;
        }
        var curHp = gadget.getFightProperties().get(FightProperty.FIGHT_PROP_CUR_HP.getId());
        var maxHp = gadget.getFightProperties().get(FightProperty.FIGHT_PROP_BASE_HP.getId());
        int percent = (int) (curHp / maxHp);

        if (percent != lastSendPercent) {
            challenge.getScene().broadcastPacket(new PacketChallengeDataNotify(challenge, 2, percent));
            lastSendPercent = percent;
        }

        if (percent <= 0) {
            challenge.fail();
        }
    }
}
