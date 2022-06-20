package emu.grasscutter.scripts;

import emu.grasscutter.game.dungeons.challenge.DungeonChallenge;
import emu.grasscutter.game.dungeons.challenge.factory.ChallengeFactory;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.server.packet.send.PacketCanUseSkillNotify;
import emu.grasscutter.server.packet.send.PacketWorktopOptionNotify;
import io.netty.util.concurrent.FastThreadLocal;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ScriptLib {
    public static final Logger logger = LoggerFactory.getLogger(ScriptLib.class);
    private final FastThreadLocal<SceneScriptManager> sceneScriptManager;
    private final FastThreadLocal<SceneGroup> currentGroup;

    public ScriptLib() {
        this.sceneScriptManager = new FastThreadLocal<>();
        this.currentGroup = new FastThreadLocal<>();
    }

    public void setSceneScriptManager(SceneScriptManager sceneScriptManager) {
        this.sceneScriptManager.set(sceneScriptManager);
    }

    public void removeSceneScriptManager() {
        this.sceneScriptManager.remove();
    }

    public SceneScriptManager getSceneScriptManager() {
        // normally not null
        return Optional.of(this.sceneScriptManager.get()).get();
    }

    private String printTable(LuaTable table) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (var meta : table.keys()) {
            sb.append(meta).append(":").append(table.get(meta)).append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    public void setCurrentGroup(SceneGroup currentGroup) {
        this.currentGroup.set(currentGroup);
    }

    public Optional<SceneGroup> getCurrentGroup() {
        return Optional.of(this.currentGroup.get());
    }

    public void removeCurrentGroup() {
        this.currentGroup.remove();
    }

    public int SetGadgetStateByConfigId(int configId, int gadgetState) {
        logger.debug("[LUA] Call SetGadgetStateByConfigId with {},{}",
            configId, gadgetState);
        Optional<GameEntity> entity = this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getConfigId() == configId).findFirst();

        if (entity.isEmpty()) {
            return 1;
        }

        if (entity.get() instanceof EntityGadget entityGadget) {
            entityGadget.updateState(gadgetState);
            return 0;
        }

        return 1;
    }

    public int SetGroupGadgetStateByConfigId(int groupId, int configId, int gadgetState) {
        logger.debug("[LUA] Call SetGroupGadgetStateByConfigId with {},{},{}",
            groupId, configId, gadgetState);

        this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getGroupId() == groupId)
            .filter(e -> e instanceof EntityGadget)
            .map(e -> (EntityGadget) e)
            .forEach(e -> e.updateState(gadgetState));

        return 0;
    }

    public int SetWorktopOptionsByGroupId(int groupId, int configId, int[] options) {
        logger.debug("[LUA] Call SetWorktopOptionsByGroupId with {},{},{}",
            groupId, configId, options);

        Optional<GameEntity> entity = this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getConfigId() == configId && e.getGroupId() == groupId).findFirst();


        if (entity.isEmpty() || !(entity.get() instanceof EntityGadget gadget)) {
            return 1;
        }

        if (!(gadget.getContent() instanceof GadgetWorktop worktop)) {
            return 1;
        }

        worktop.addWorktopOptions(options);
        this.getSceneScriptManager().getScene().broadcastPacket(new PacketWorktopOptionNotify(gadget));

        return 0;
    }

    public int SetWorktopOptions(LuaTable table) {
        logger.debug("[LUA] Call SetWorktopOptions with {}", this.printTable(table));
        // TODO
        return 0;
    }

    public int DelWorktopOptionByGroupId(int groupId, int configId, int option) {
        logger.debug("[LUA] Call DelWorktopOptionByGroupId with {},{},{}", groupId, configId, option);

        Optional<GameEntity> entity = this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getConfigId() == configId && e.getGroupId() == groupId).findFirst();

        if (entity.isEmpty() || !(entity.get() instanceof EntityGadget gadget)) {
            return 1;
        }

        if (!(gadget.getContent() instanceof GadgetWorktop worktop)) {
            return 1;
        }

        worktop.removeWorktopOption(option);
        this.getSceneScriptManager().getScene().broadcastPacket(new PacketWorktopOptionNotify(gadget));

        return 0;
    }

    // Some fields are guessed
    public int AutoMonsterTide(int challengeIndex, int groupId, Integer[] ordersConfigId, int tideCount, int sceneLimit, int param6) {
        logger.debug("[LUA] Call AutoMonsterTide with {},{},{},{},{},{}",
            challengeIndex, groupId, ordersConfigId, tideCount, sceneLimit, param6);

        SceneGroup group = this.getSceneScriptManager().getGroupById(groupId);

        if (group == null || group.monsters == null) {
            return 1;
        }

        this.getSceneScriptManager().startMonsterTideInGroup(group, ordersConfigId, tideCount, sceneLimit);

        return 0;
    }

    public int AddExtraGroupSuite(int groupId, int suite) {
        logger.debug("[LUA] Call AddExtraGroupSuite with {},{}",
            groupId, suite);
        SceneGroup group = this.getSceneScriptManager().getGroupById(groupId);

        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if (suiteData == null) {
            return 1;
        }
        // avoid spawn wrong monster
        if (this.getSceneScriptManager().getScene().getChallenge() != null)
            if (!this.getSceneScriptManager().getScene().getChallenge().inProgress() ||
                this.getSceneScriptManager().getScene().getChallenge().getGroup().id != groupId) {
                return 0;
            }
        this.getSceneScriptManager().addGroupSuite(group, suiteData);

        return 0;
    }

    public int GoToGroupSuite(int groupId, int suite) {
        logger.debug("[LUA] Call GoToGroupSuite with {},{}",
            groupId, suite);
        SceneGroup group = this.getSceneScriptManager().getGroupById(groupId);
        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if (suiteData == null) {
            return 1;
        }

        for (var suiteItem : group.suites) {
            if (suiteData == suiteItem) {
                continue;
            }
            this.getSceneScriptManager().removeGroupSuite(group, suiteItem);
        }
        this.getSceneScriptManager().addGroupSuite(group, suiteData);

        return 0;
    }

    public int RemoveExtraGroupSuite(int groupId, int suite) {
        logger.debug("[LUA] Call RemoveExtraGroupSuite with {},{}",
            groupId, suite);

        SceneGroup group = this.getSceneScriptManager().getGroupById(groupId);
        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if (suiteData == null) {
            return 1;
        }

        this.getSceneScriptManager().removeGroupSuite(group, suiteData);

        return 0;
    }

    public int KillExtraGroupSuite(int groupId, int suite) {
        logger.debug("[LUA] Call KillExtraGroupSuite with {},{}",
            groupId, suite);

        SceneGroup group = this.getSceneScriptManager().getGroupById(groupId);
        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if (suiteData == null) {
            return 1;
        }

        this.getSceneScriptManager().removeGroupSuite(group, suiteData);

        return 0;
    }

    // param3 (probably time limit for timed dungeons)
    public int ActiveChallenge(int challengeId, int challengeIndex, int timeLimitOrGroupId, int groupId, int objectiveKills, int param5) {
        logger.debug("[LUA] Call ActiveChallenge with {},{},{},{},{},{}",
            challengeId, challengeIndex, timeLimitOrGroupId, groupId, objectiveKills, param5);

        var challenge = ChallengeFactory.getChallenge(
            challengeId,
            challengeIndex,
            timeLimitOrGroupId,
            groupId,
            objectiveKills,
            param5,
            this.getSceneScriptManager().getScene(),
            this.getCurrentGroup().get()
        );

        if (challenge == null) {
            return 1;
        }

        if (challenge instanceof DungeonChallenge dungeonChallenge) {
            // set if tower first stage (6-1)
            dungeonChallenge.setStage(this.getSceneScriptManager().getVariables().getOrDefault("stage", -1) == 0);
        }

        this.getSceneScriptManager().getScene().setChallenge(challenge);
        challenge.start();
        return 0;
    }

    public int GetGroupMonsterCountByGroupId(int groupId) {
        logger.debug("[LUA] Call GetGroupMonsterCountByGroupId with {}",
            groupId);
        return (int) this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityMonster && e.getGroupId() == groupId)
            .count();
    }

    public int GetGroupVariableValue(String var) {
        logger.debug("[LUA] Call GetGroupVariableValue with {}",
            var);
        return this.getSceneScriptManager().getVariables().getOrDefault(var, 0);
    }

    public int SetGroupVariableValue(String var, int value) {
        logger.debug("[LUA] Call SetGroupVariableValue with {},{}",
            var, value);
        this.getSceneScriptManager().getVariables().put(var, value);
        return 0;
    }

    public LuaValue ChangeGroupVariableValue(String var, int value) {
        logger.debug("[LUA] Call ChangeGroupVariableValue with {},{}",
            var, value);

        this.getSceneScriptManager().getVariables().put(var, this.getSceneScriptManager().getVariables().get(var) + value);
        return LuaValue.ZERO;
    }

    /**
     * Set the actions and triggers to designated group
     */
    public int RefreshGroup(LuaTable table) {
        logger.debug("[LUA] Call RefreshGroup with {}",
            this.printTable(table));
        // Kill and Respawn?
        int groupId = table.get("group_id").toint();
        int suite = table.get("suite").toint();

        SceneGroup group = this.getSceneScriptManager().getGroupById(groupId);

        if (group == null || group.monsters == null) {
            return 1;
        }

        this.getSceneScriptManager().refreshGroup(group, suite);

        return 0;
    }

    public int GetRegionEntityCount(LuaTable table) {
        logger.debug("[LUA] Call GetRegionEntityCount with {}",
            this.printTable(table));
        int regionId = table.get("region_eid").toint();
        int entityType = table.get("entity_type").toint();

        SceneRegion region = this.getSceneScriptManager().getRegionById(regionId);

        if (region == null) {
            return 0;
        }

        return (int) region.getEntities().intStream().filter(e -> e >> 24 == entityType).count();
    }

    public void PrintContextLog(String msg) {
        logger.info("[LUA] " + msg);
    }

    public int TowerCountTimeStatus(int isDone, int var2) {
        logger.debug("[LUA] Call TowerCountTimeStatus with {},{}",
            isDone, var2);
        // TODO record time
        return 0;
    }

    public int GetGroupMonsterCount() {
        logger.debug("[LUA] Call GetGroupMonsterCount ");

        return (int) this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityMonster &&
                e.getGroupId() == this.getCurrentGroup().map(sceneGroup -> sceneGroup.id).orElse(-1))
            .count();
    }

    public int SetMonsterBattleByGroup(int var1, int var2, int var3) {
        logger.debug("[LUA] Call SetMonsterBattleByGroup with {},{},{}",
            var1, var2, var3);
        // TODO
        return 0;
    }

    public int CauseDungeonFail(int var1) {
        logger.debug("[LUA] Call CauseDungeonFail with {}",
            var1);

        return 0;
    }

    public int GetGroupVariableValueByGroup(String name, int groupId) {
        logger.debug("[LUA] Call GetGroupVariableValueByGroup with {},{}",
            name, groupId);

        return this.getSceneScriptManager().getVariables().getOrDefault(name, 0);
    }

    public int SetIsAllowUseSkill(int canUse, int var2) {
        logger.debug("[LUA] Call SetIsAllowUseSkill with {},{}",
            canUse, var2);

        this.getSceneScriptManager().getScene().broadcastPacket(new PacketCanUseSkillNotify(canUse == 1));
        return 0;
    }

    public int KillEntityByConfigId(LuaTable table) {
        logger.debug("[LUA] Call KillEntityByConfigId with {}",
            this.printTable(table));
        var configId = table.get("config_id");
        if (configId == LuaValue.NIL) {
            return 1;
        }

        var entity = this.getSceneScriptManager().getScene().getEntityByConfigId(configId.toint());
        if (entity == null) {
            return 0;
        }
        this.getSceneScriptManager().getScene().killEntity(entity, 0);
        return 0;
    }

    public int SetGroupVariableValueByGroup(String key, int value, int groupId) {
        logger.debug("[LUA] Call SetGroupVariableValueByGroup with {},{},{}",
            key, value, groupId);

        this.getSceneScriptManager().getVariables().put(key, value);
        return 0;
    }

    public int CreateMonster(LuaTable table) {
        logger.debug("[LUA] Call CreateMonster with {}",
            this.printTable(table));
        var configId = table.get("config_id").toint();
        var delayTime = table.get("delay_time").toint();

        if (this.getCurrentGroup().isEmpty()) {
            return 1;
        }

        this.getSceneScriptManager().spawnMonstersByConfigId(this.getCurrentGroup().get(), configId, delayTime);
        return 0;
    }

    public int TowerMirrorTeamSetUp(int team, int var1) {
        logger.debug("[LUA] Call TowerMirrorTeamSetUp with {},{}",
            team, var1);

        this.getSceneScriptManager().unloadCurrentMonsterTide();
        this.getSceneScriptManager().getScene().getPlayers().get(0).getTowerManager().mirrorTeamSetUp(team - 1);

        return 0;
    }

    public int CreateGadget(LuaTable table) {
        logger.debug("[LUA] Call CreateGadget with {}",
            this.printTable(table));
        var configId = table.get("config_id").toint();

        var group = this.getCurrentGroup();

        if (group.isEmpty()) {
            return 1;
        }

        var gadget = group.get().gadgets.get(configId);
        var entity = this.getSceneScriptManager().createGadget(group.get().id, group.get().block_id, gadget);

        this.getSceneScriptManager().addEntity(entity);

        return 0;
    }

    public int CheckRemainGadgetCountByGroupId(LuaTable table) {
        logger.debug("[LUA] Call CheckRemainGadgetCountByGroupId with {}",
            this.printTable(table));
        var groupId = table.get("group_id").toint();

        var count = this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == groupId)
            .count();
        return (int) count;
    }

    public int GetGadgetStateByConfigId(int groupId, int configId) {
        logger.debug("[LUA] Call GetGadgetStateByConfigId with {},{}",
            groupId, configId);

        if (groupId == 0) {
            groupId = this.getCurrentGroup().get().id;
        }
        final int realGroupId = groupId;
        var gadget = this.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == realGroupId)
            .filter(g -> g.getConfigId() == configId)
            .findFirst();
        if (gadget.isEmpty()) {
            return 1;
        }
        return ((EntityGadget) gadget.get()).getState();
    }

    public int MarkPlayerAction(int var1, int var2, int var3, int var4) {
        logger.debug("[LUA] Call MarkPlayerAction with {},{},{},{}",
            var1, var2, var3, var4);

        return 0;
    }

    public int AddQuestProgress(String var1) {
        logger.debug("[LUA] Call AddQuestProgress with {}",
            var1);

        return 0;
    }

    /**
     * change the state of gadget
     */
    public int ChangeGroupGadget(LuaTable table) {
        logger.debug("[LUA] Call ChangeGroupGadget with {}",
            this.printTable(table));
        var configId = table.get("config_id").toint();
        var state = table.get("state").toint();

        var entity = this.getSceneScriptManager().getScene().getEntityByConfigId(configId);
        if (entity == null) {
            return 1;
        }

        if (entity instanceof EntityGadget entityGadget) {
            entityGadget.updateState(state);
            return 0;
        }

        return 1;
    }
}
