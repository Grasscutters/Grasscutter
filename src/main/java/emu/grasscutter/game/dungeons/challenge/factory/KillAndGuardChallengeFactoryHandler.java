package emu.grasscutter.game.dungeons.challenge.factory;

import static emu.grasscutter.game.dungeons.challenge.enums.ChallengeType.CHALLENGE_KILL_COUNT_GUARD_HP;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.dungeons.challenge.enums.ChallengeType;
import emu.grasscutter.game.dungeons.challenge.trigger.*;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;
import java.util.List;
import lombok.val;

public class KillAndGuardChallengeFactoryHandler implements ChallengeFactoryHandler {
    @Override
    public boolean isThisType(ChallengeType challengeType) {
        // ActiveChallenge with 1,188,234101003,12,3030,0
        return challengeType == CHALLENGE_KILL_COUNT_GUARD_HP;
    }

    @Override /*TODO check param4 == monstesToKill*/
    public WorldChallenge build(
            int challengeIndex,
            int challengeId,
            int groupId,
            int monstersToKill,
            int gadgetCFGId,
            int unused,
            Scene scene,
            SceneGroup group) {
        val realGroup = scene.getScriptManager().getGroupById(groupId);
        return new WorldChallenge(
                scene,
                realGroup,
                challengeId, // Id
                challengeIndex, // Index
                List.of(monstersToKill, 0),
                0, // Limit
                monstersToKill, // Goal
                List.of(new KillMonsterCountTrigger(), new GuardTrigger(gadgetCFGId)));
    }
}
