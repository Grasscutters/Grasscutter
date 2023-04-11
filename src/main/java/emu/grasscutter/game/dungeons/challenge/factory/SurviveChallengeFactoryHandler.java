package emu.grasscutter.game.dungeons.challenge.factory;

import static emu.grasscutter.game.dungeons.challenge.enums.ChallengeType.CHALLENGE_SURVIVE;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.dungeons.challenge.enums.ChallengeType;
import emu.grasscutter.game.dungeons.challenge.trigger.ForTimeTrigger;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;
import java.util.List;

public class SurviveChallengeFactoryHandler implements ChallengeFactoryHandler {
    @Override
    public boolean isThisType(ChallengeType challengeType) {
        // grp 201055005
        // ActiveChallenge with 100, 56, 60, 0, 0, 0
        return challengeType == CHALLENGE_SURVIVE;
    }

    @Override
    public WorldChallenge build(
            int challengeIndex,
            int challengeId,
            int timeToSurvive,
            int unused4,
            int unused5,
            int unused6,
            Scene scene,
            SceneGroup group) {
        return new WorldChallenge(
                scene,
                group,
                challengeId, // Id
                challengeIndex, // Index
                List.of(timeToSurvive),
                timeToSurvive, // Limit
                0, // Goal
                List.of(new ForTimeTrigger()));
    }
}
