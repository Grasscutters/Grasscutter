package emu.grasscutter.game.dungeons.challenge;

import emu.grasscutter.game.dungeons.challenge.trigger.ChallengeTrigger;
import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneGroup;

import java.util.List;

public final class DungeonChallenge extends WorldChallenge {

    /**
     * has more challenge
     */
    private boolean stage;

    public DungeonChallenge(Scene scene, SceneGroup group,
                            int challengeId, int challengeIndex,
                            List<Integer> paramList,
                            int timeLimit, int goal,
                            List<ChallengeTrigger> challengeTriggers) {
        super(scene, group, challengeId, challengeIndex, paramList, timeLimit, goal, challengeTriggers);
    }

    public boolean isStage() {
        return stage;
    }

    public void setStage(boolean stage) {
        this.stage = stage;
    }

    @Override
    public void done() {
        super.done();
        this.getScene().triggerDungeonEvent(
            DungeonPassConditionType.DUNGEON_COND_FINISH_CHALLENGE,
            this.getChallengeId(), this.getChallengeIndex());

        if (this.isSuccess())
            this.settle();
    }

    private void settle() {
        if (!stage) {
            var scene = this.getScene();
            /*if(this.isSuccess()){
                scene.getDungeonManager().finishDungeon();
            } else {
                scene.getDungeonManager().failDungeon();
            }*/
        }
    }

}
