package emu.grasscutter.game.dungeons.challenge.factory;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.dungeons.challenge.enums.ChallengeType;
import emu.grasscutter.game.dungeons.challenge.trigger.*;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;
import java.util.List;
import lombok.val;

public class KillMonsterTimeChallengeFactoryHandler implements ChallengeFactoryHandler {
    @Override
    public boolean isThisType(ChallengeType challengeType) {
        // ActiveChallenge with 180,180,45,133108061,1,0
        // ActiveChallenge Fast with 1001, 5, 15, 240004005, 10, 0
        return challengeType == ChallengeType.CHALLENGE_KILL_COUNT_IN_TIME
                || challengeType == ChallengeType.CHALLENGE_KILL_COUNT_FAST;
    }

    @Override
    public WorldChallenge build(
            int challengeIndex,
            int challengeId,
            int timeLimit,
            int groupId,
            int targetCount,
            int param6,
            Scene scene,
            SceneGroup group) {
        val realGroup = scene.getScriptManager().getGroupById(groupId);
        return new WorldChallenge(
                scene,
                realGroup,
                challengeId, // Id
                challengeIndex, // Index
                List.of(targetCount, timeLimit),
                timeLimit, // Limit
                targetCount, // Goal
                List.of(new KillMonsterCountTrigger(), new InTimeTrigger()));
    }
}
