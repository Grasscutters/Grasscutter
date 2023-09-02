package emu.grasscutter.game.dungeons.challenge;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.dungeons.challenge.trigger.ChallengeTrigger;
import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.server.packet.send.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.*;

@Getter
@Setter
public class WorldChallenge {
    private final Scene scene;
    private final SceneGroup group;
    private final int challengeId;
    private final int challengeIndex;
    private final List<Integer> paramList;
    private int timeLimit;
    private final List<ChallengeTrigger> challengeTriggers;
    private final int goal;
    private final AtomicInteger score;
    private boolean progress;
    private boolean success;
    private long startedAt;
    private int finishedTime;

    /**
     * @param scene The scene the challenge is in.
     * @param group The group the challenge is in.
     * @param challengeId The challenge's id.
     * @param challengeIndex The challenge's index.
     * @param paramList The challenge's parameters.
     * @param timeLimit The challenge's time limit.
     * @param goal The challenge's goal.
     * @param challengeTriggers The challenge's triggers.
     */
    public WorldChallenge(
            Scene scene,
            SceneGroup group,
            int challengeId,
            int challengeIndex,
            List<Integer> paramList,
            int timeLimit,
            int goal,
            List<ChallengeTrigger> challengeTriggers) {
        this.scene = scene;
        this.group = group;
        this.challengeId = challengeId;
        this.challengeIndex = challengeIndex;
        this.paramList = paramList;
        this.timeLimit = timeLimit;
        this.challengeTriggers = challengeTriggers;
        this.goal = goal;
        this.score = new AtomicInteger(0);
    }

    public boolean inProgress() {
        return this.progress;
    }

    public void onCheckTimeOut() {
        if (!inProgress()) {
            return;
        }
        if (timeLimit <= 0) {
            return;
        }
        challengeTriggers.forEach(t -> t.onCheckTimeout(this));
    }

    public void start() {
        if (inProgress()) {
            Grasscutter.getLogger().debug("Could not start a in progress challenge.");
            return;
        }
        this.progress = true;
        this.startedAt = System.currentTimeMillis();
        getScene().broadcastPacket(new PacketDungeonChallengeBeginNotify(this));
        challengeTriggers.forEach(t -> t.onBegin(this));
    }

    public void done() {
        if (!this.inProgress()) return;
        this.finish(true);

        var scene = this.getScene();
        var scriptManager = scene.getScriptManager();
        var dungeonManager = scene.getDungeonManager();
        if (dungeonManager != null && dungeonManager.getDungeonData() != null) {
            scene
                    .getPlayers()
                    .forEach(
                            p ->
                                    p.getActivityManager()
                                            .triggerWatcher(
                                                    WatcherTriggerType.TRIGGER_FINISH_CHALLENGE,
                                                    String.valueOf(dungeonManager.getDungeonData().getId()),
                                                    String.valueOf(this.getGroup().id),
                                                    String.valueOf(this.getChallengeId())));
        }

        // TODO: record the time in PARAM2 and used in action
        scriptManager.callEvent(
                new ScriptArgs(this.getGroup().id, EventType.EVENT_CHALLENGE_SUCCESS)
                        .setParam2(finishedTime)
                        .setEventSource(this.getChallengeIndex()));

        this.getScene()
                .triggerDungeonEvent(
                        DungeonPassConditionType.DUNGEON_COND_FINISH_CHALLENGE,
                        getChallengeId(),
                        getChallengeIndex());

        this.challengeTriggers.forEach(t -> t.onFinish(this));
    }

    public void fail() {
        if (!this.inProgress()) return;
        this.finish(false);

        // TODO: Set 'eventSource' in script arguments.
        var scriptManager = this.getScene().getScriptManager();
        scriptManager.callEvent(
                new ScriptArgs(this.getGroup().id, EventType.EVENT_CHALLENGE_FAIL)
                        .setEventSource(this.getChallengeIndex()));
        challengeTriggers.forEach(t -> t.onFinish(this));
    }

    private void finish(boolean success) {
        this.progress = false;
        this.success = success;
        this.finishedTime = (int) ((this.scene.getSceneTimeSeconds() - this.startedAt));
        getScene().broadcastPacket(new PacketDungeonChallengeFinishNotify(this));
    }

    public int increaseScore() {
        return score.incrementAndGet();
    }

    public void onMonsterDeath(EntityMonster monster) {
        if (!inProgress()) {
            return;
        }
        if (monster.getGroupId() != getGroup().id) {
            return;
        }
        this.challengeTriggers.forEach(t -> t.onMonsterDeath(this, monster));
    }

    public void onGadgetDeath(EntityGadget gadget) {
        if (!inProgress()) {
            return;
        }
        if (gadget.getGroupId() != getGroup().id) {
            return;
        }
        this.challengeTriggers.forEach(t -> t.onGadgetDeath(this, gadget));
    }

    public void onGroupTriggerDeath(SceneTrigger trigger) {
        if (!this.inProgress()) return;

        var triggerGroup = trigger.getCurrentGroup();
        if (triggerGroup == null || triggerGroup.id != getGroup().id) {
            return;
        }

        this.challengeTriggers.forEach(t -> t.onGroupTrigger(this, trigger));
    }

    public void onGadgetDamage(EntityGadget gadget) {
        if (!inProgress()) {
            return;
        }
        if (gadget.getGroupId() != getGroup().id) {
            return;
        }
        this.challengeTriggers.forEach(t -> t.onGadgetDamage(this, gadget));
    }
}
