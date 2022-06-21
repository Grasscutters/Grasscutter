package emu.grasscutter.game.dungeons.challenge.factory;

import emu.grasscutter.game.dungeons.challenge.WorldChallenge;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;

public interface ChallengeFactoryHandler {
    boolean isThisType(int challengeIndex, int challengeId, int param3, int param4, int param5, int param6, Scene scene, SceneGroup group);

    WorldChallenge build(int challengeIndex, int challengeId, int param3, int param4, int param5, int param6, Scene scene, SceneGroup group);
}
