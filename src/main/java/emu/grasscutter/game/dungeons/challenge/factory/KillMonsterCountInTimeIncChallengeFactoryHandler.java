package emu.grasscutter.game.dungeons.challenge.factory;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.dungeons.challenge.enums.ChallengeType;
import emu.grasscutter.game.dungeons.challenge.trigger.*;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;
import java.util.List;
import lombok.val;

public class KillMonsterCountInTimeIncChallengeFactoryHandler implements ChallengeFactoryHandler {
    @Override
    public boolean isThisType(ChallengeType challengeType) {
        return challengeType == ChallengeType.CHALLENGE_TIME_FLY;
    }

    @Override
    public WorldChallenge build(
            int challengeIndex,
            int challengeId,
            int groupId,
            int monsterCount,
            int timeLimit,
            int timeInc,
            Scene scene,
            SceneGroup group) {
        val realGroup = scene.getScriptManager().getGroupById(groupId);
        return new WorldChallenge(
                scene,
                realGroup,
                challengeId, // Id
                challengeIndex, // Index
                List.of(monsterCount, timeLimit, timeInc),
                timeLimit, // Limit
                monsterCount, // Goal
                List.of(
                        new KillMonsterCountTrigger(),
                        new InTimeTrigger(),
                        new KillMonsterTimeIncTrigger(timeInc)));
    }
}
