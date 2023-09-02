package emu.grasscutter.game.dungeons.challenge.factory;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;
import java.util.*;
import lombok.val;

public abstract class ChallengeFactory {
    private static final List<ChallengeFactoryHandler> challengeFactoryHandlers = new ArrayList<>();

    static {
        challengeFactoryHandlers.add(new KillAndGuardChallengeFactoryHandler());
        challengeFactoryHandlers.add(new KillMonsterCountChallengeFactoryHandler());
        challengeFactoryHandlers.add(new KillMonsterInTimeChallengeFactoryHandler());
        challengeFactoryHandlers.add(new KillMonsterTimeChallengeFactoryHandler());
        challengeFactoryHandlers.add(new SurviveChallengeFactoryHandler());
        challengeFactoryHandlers.add(new TriggerInTimeChallengeFactoryHandler());
        challengeFactoryHandlers.add(new KillMonsterCountInTimeIncChallengeFactoryHandler());
    }

    public static WorldChallenge getChallenge(
            int localChallengeId,
            int challengeDataId,
            int param3,
            int param4,
            int param5,
            int param6,
            Scene scene,
            SceneGroup group) {
        val challengeData = GameData.getDungeonChallengeConfigDataMap().get(challengeDataId);
        val challengeType = challengeData.getChallengeType();

        for (var handler : challengeFactoryHandlers) {
            if (!handler.isThisType(challengeType)) {
                continue;
            }
            return handler.build(
                    localChallengeId, challengeDataId, param3, param4, param5, param6, scene, group);
        }
        return null;
    }
}
