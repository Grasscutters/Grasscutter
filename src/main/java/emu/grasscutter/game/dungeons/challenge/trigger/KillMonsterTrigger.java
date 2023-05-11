package emu.grasscutter.game.dungeons.challenge.trigger;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.server.packet.send.PacketChallengeDataNotify;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KillMonsterTrigger extends ChallengeTrigger {
    private int monsterCfgId;

    @Override
    public void onBegin(WorldChallenge challenge) {
        challenge
                .getScene()
                .broadcastPacket(new PacketChallengeDataNotify(challenge, 1, challenge.getScore().get()));
    }

    @Override
    public void onMonsterDeath(WorldChallenge challenge, EntityMonster monster) {
        if (monster.getConfigId() == monsterCfgId) {
            challenge.done();
        }
    }
}
