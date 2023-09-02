package emu.grasscutter.game.dungeons.challenge.factory;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.dungeons.challenge.enums.ChallengeType;
import emu.grasscutter.game.dungeons.challenge.trigger.KillMonsterCountTrigger;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;
import java.util.List;
import lombok.val;

public class KillMonsterCountChallengeFactoryHandler implements ChallengeFactoryHandler {
    @Override
    public boolean isThisType(ChallengeType challengeType) {
        // ActiveChallenge with 1, 1, 241033003, 15, 0, 0
        return challengeType == ChallengeType.CHALLENGE_KILL_COUNT;
    }

    @Override
    public WorldChallenge build(
            int challengeIndex,
            int challengeId,
            int groupId,
            int goal,
            int param5,
            int param6,
            Scene scene,
            SceneGroup group) {
        val realGroup = scene.getScriptManager().getGroupById(groupId);
        return new WorldChallenge(
                scene,
                realGroup,
                challengeId, // Id
                challengeIndex, // Index
                List.of(goal, groupId),
                0, // Limit
                goal, // Goal
                List.of(new KillMonsterCountTrigger()));
    }
}
