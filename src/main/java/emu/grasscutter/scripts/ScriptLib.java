package emu.grasscutter.scripts;

import com.esotericsoftware.reflectasm.MethodAccess;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.dungeons.challenge.DungeonChallenge;
import emu.grasscutter.game.dungeons.challenge.factory.ChallengeFactory;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.server.packet.send.PacketCanUseSkillNotify;
import emu.grasscutter.server.packet.send.PacketWorktopOptionNotify;
import org.terasology.jnlua.LuaState;
import org.terasology.jnlua.NamedJavaFunction;
import org.terasology.jnlua.util.AbstractTableMap;

import javax.script.ScriptContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptLib {

    public int SetGadgetStateByConfigId(ScriptLibContext context, int configId, int gadgetState) {
        Optional<GameEntity> entity = context.getSceneScriptManager().getScene().getEntities().values().stream()
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

    public int SetGroupGadgetStateByConfigId(ScriptLibContext context, int groupId, int configId, int gadgetState) {

        context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getGroupId() == groupId)
            .filter(e -> e instanceof EntityGadget)
            .map(e -> (EntityGadget)e)
            .forEach(e -> e.updateState(gadgetState));

        return 0;
    }

    public int SetWorktopOptionsByGroupId(ScriptLibContext context, int groupId, int configId, Object options) {

        Optional<GameEntity> entity = context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getConfigId() == configId && e.getGroupId() == groupId).findFirst();


        if (entity.isEmpty() || !(entity.get() instanceof EntityGadget gadget)) {
            return 1;
        }

        if (!(gadget.getContent() instanceof GadgetWorktop worktop)) {
            return 1;
        }

        worktop.addWorktopOptions(ScriptUtils.toIntArray(options));
        context.getSceneScriptManager().getScene().broadcastPacket(new PacketWorktopOptionNotify(gadget));

        return 0;
    }

    public EntityType GetEntityType(int tid) {
        return EntityType.Avatar;
    }

    public int SetWorktopOptions(ScriptLibContext context, Object table_){

        // TODO
        return 0;
    }
    public int DelWorktopOptionByGroupId(ScriptLibContext context, int groupId, int configId, int option) {
        Optional<GameEntity> entity = context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getConfigId() == configId && e.getGroupId() == groupId).findFirst();

        if (entity.isEmpty() || !(entity.get() instanceof EntityGadget gadget)) {
            return 1;
        }

        if (!(gadget.getContent() instanceof GadgetWorktop worktop)) {
            return 1;
        }

        worktop.removeWorktopOption(option);
        context.getSceneScriptManager().getScene().broadcastPacket(new PacketWorktopOptionNotify(gadget));

        return 0;
    }

    // Some fields are guessed
    public int AutoMonsterTide(ScriptLibContext context, int challengeIndex, int groupId, Object ordersConfigId_, int tideCount, int sceneLimit, int param6) {
        var ordersConfigId = ScriptUtils.toIntegerArray(ordersConfigId_);
        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);

        if (group == null || group.monsters == null) {
            return 1;
        }

        context.getSceneScriptManager().startMonsterTideInGroup(group, ordersConfigId, tideCount, sceneLimit);

        return 0;
    }

    public int AddExtraGroupSuite(ScriptLibContext context, int groupId, int suite) {
        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);

        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if(suiteData == null){
            return 1;
        }
        // avoid spawn wrong monster
//		if(context.getSceneScriptManager().getScene().getChallenge() != null)
//			if(!context.getSceneScriptManager().getScene().getChallenge().inProgress() ||
//					context.getSceneScriptManager().getScene().getChallenge().getGroup().id != groupId){
//			return 0;
//		}
        context.getSceneScriptManager().addGroupSuite(group, suiteData);

        return 0;
    }
    public int GoToGroupSuite(ScriptLibContext context, int groupId, int suite) {
        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);
        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if(suiteData == null){
            return 1;
        }

        for(var suiteItem : group.suites){
            if(suiteData == suiteItem){
                continue;
            }
            context.getSceneScriptManager().removeGroupSuite(group, suiteItem);
        }
        context.getSceneScriptManager().addGroupSuite(group, suiteData);

        return 0;
    }
    public int RemoveExtraGroupSuite(ScriptLibContext context, int groupId, int suite) {

        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);
        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if(suiteData == null){
            return 1;
        }

        context.getSceneScriptManager().removeGroupSuite(group, suiteData);

        return 0;
    }
    public int KillExtraGroupSuite(ScriptLibContext context, int groupId, int suite) {

        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);
        if (group == null || group.monsters == null) {
            return 1;
        }
        var suiteData = group.getSuiteByIndex(suite);
        if(suiteData == null){
            return 1;
        }

        context.getSceneScriptManager().removeGroupSuite(group, suiteData);

        return 0;
    }
    // param3 (probably time limit for timed dungeons)
    public int ActiveChallenge(ScriptLibContext context, int challengeId, int challengeIndex, int timeLimitOrGroupId, int groupId, int objectiveKills, int param5) {

        var challenge = ChallengeFactory.getChallenge(
            challengeId,
            challengeIndex,
            timeLimitOrGroupId,
            groupId,
            objectiveKills,
            param5,
            context.getSceneScriptManager().getScene(),
            context.getCurrentGroup()
        );

        if(challenge == null){
            return 1;
        }

        if(challenge instanceof DungeonChallenge dungeonChallenge){
            // set if tower first stage (6-1)
            dungeonChallenge.setStage(context.getSceneScriptManager()
                .getVariables(context.getCurrentGroup().id).getOrDefault("stage", -1) == 0);
        }

        context.getSceneScriptManager().getScene().setChallenge(challenge);
        challenge.start();
        return 0;
    }

    public int GetGroupMonsterCountByGroupId(ScriptLibContext context, int groupId) {
        return (int) context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityMonster && e.getGroupId() == groupId)
            .count();
    }

    public int GetGroupVariableValue(ScriptLibContext context, String var) {
        return context.getSceneScriptManager().getVariables(context.getCurrentGroup().id).getOrDefault(var, 0);
    }

    public int SetGroupVariableValue(ScriptLibContext context, String var, int value) {
        context.getSceneScriptManager().getVariables(context.getCurrentGroup().id).put(var, value);
        return 0;
    }

    public int ChangeGroupVariableValue(ScriptLibContext context, String var, int value) {
        context.getSceneScriptManager().getVariables(context.getCurrentGroup().id)
            .compute(var, (k,v) -> v == null ? value : v + value);
        return 0;
    }

    /**
     * Set the actions and triggers to designated group
     */
    public int RefreshGroup(ScriptLibContext context, Object table_) {
        var table = (AbstractTableMap)table_;
        // Kill and Respawn?
        int groupId = ((Integer)table.get("group_id"));
        int suite = ((Integer)table.get("suite"));

        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);

        if (group == null || group.monsters == null) {
            return 1;
        }


        context.getSceneScriptManager().refreshGroup(group, suite);

        return 0;
    }

    public int GetRegionEntityCount(ScriptLibContext context, Object table_) {
        var table = (AbstractTableMap)table_;
        int regionId = ScriptUtils.getInt(table.get("region_eid"));
        int entityType = ScriptUtils.getInt(table.get("entity_type"));

        var region = context.getSceneScriptManager().getRegionById(regionId);

        if (region == null) {
            return 0;
        }

        return (int) region.getEntities().values().stream().filter(e -> e.getEntityType() == entityType).count();
    }

    public void PrintContextLog(ScriptLibContext context, String msg) {
        Grasscutter.getLogger().info("[LUA] " + msg);
    }

    public int TowerCountTimeStatus(ScriptLibContext context, int isDone){

        // TODO record time
        return 0;
    }
    public int GetGroupMonsterCount(ScriptLibContext context){
        return (int) context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityMonster &&
                e.getGroupId() == context.getCurrentGroup().id)
            .count();
    }
    public int SetMonsterBattleByGroup(ScriptLibContext context, int var2, int var3){
        // TODO
        return 0;
    }

    public int CauseDungeonFail(ScriptLibContext context){

        return 0;
    }

    public int GetGroupVariableValueByGroup(ScriptLibContext context, String name, int groupId){

        return context.getSceneScriptManager().getVariables(groupId).getOrDefault(name, 0);
    }

    public int SetIsAllowUseSkill(ScriptLibContext context, int canUse){

        context.getSceneScriptManager().getScene().broadcastPacket(new PacketCanUseSkillNotify(canUse == 1));
        return 0;
    }

    public int KillEntityByConfigId(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var configId = table.get("config_id");
        if(configId == null){
            return 1;
        }

        var entity = context.getSceneScriptManager().getScene().getEntityByConfigId(((Integer)configId).intValue());
        if(entity == null){
            return 0;
        }
        context.getSceneScriptManager().getScene().killEntity(entity, 0);
        return 0;
    }

    public int SetGroupVariableValueByGroup(ScriptLibContext context, String key, int value, int groupId){

        context.getSceneScriptManager().getVariables(groupId).put(key, value);
        return 0;
    }

    public int CreateMonster(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var configId = ScriptUtils.getInt(table.get("config_id"));
        var delayTime = ScriptUtils.getInt(table.get("delay_time"));

        if(context.getCurrentGroup() == null){
            return 1;
        }

        context.getSceneScriptManager().spawnMonstersByConfigId(context.getCurrentGroup(), configId, delayTime);
        return 0;
    }

    public int TowerMirrorTeamSetUp(ScriptLibContext context, int team) {

        context.getSceneScriptManager().unloadCurrentMonsterTide();
        context.getSceneScriptManager().getScene().getPlayers().get(0).getTowerManager().mirrorTeamSetUp(team-1);

        return 0;
    }

    public int CreateGadget(ScriptLibContext context, Object table_) throws Exception {
        var table = (AbstractTableMap)table_;
        var configId = ScriptUtils.getInt(table.get("config_id"));

        var group = context.getCurrentGroup();

        if (group == null) {
            return 1;
        }

        var gadget = group.gadgets.get(configId);
        var entity = context.getSceneScriptManager().createGadget(group.id, group.block_id, gadget);
        if(entity == null) {
            System.out.println("Error to create a null-entity.");
        }
        context.getSceneScriptManager().addEntity(entity);

        return 0;
    }

    public int PrintLog(String content) {
        Grasscutter.getLogger().debug(content);

        return 0;
    }

    public int CheckRemainGadgetCountByGroupId(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var groupId = ScriptUtils.getInt(table.get("group_id"));

        var count = context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == groupId)
            .count();
        return (int)count;
    }

    public int GetGadgetStateByConfigId(ScriptLibContext context, int groupId, int configId){

        if(groupId == 0){
            groupId = context.getCurrentGroup().id;
        }
        final int realGroupId = groupId;
        var gadget = context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == realGroupId)
            .filter(g -> g.getConfigId() == configId)
            .findFirst();
        if(gadget.isEmpty()){
            return 1;
        }
        return ((EntityGadget)gadget.get()).getState();
    }

    public int MarkPlayerAction(ScriptLibContext context, int var2, int var3, int var4){

        return 0;
    }

    public int AddQuestProgress(ScriptLibContext context, String var1){

        return 0;
    }

    /**
     * change the state of gadget
     */
    public int ChangeGroupGadget(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var configId = ScriptUtils.getInt(table.get("config_id"));
        var state = ScriptUtils.getInt(table.get("state"));
        var entity = context.getSceneScriptManager().getScene().getEntityByConfigId(configId);
        if(entity == null){
            return 1;
        }

        if (entity instanceof EntityGadget entityGadget) {
            entityGadget.updateState(state);
            return 0;
        }

        return 1;
    }

    public int RemoveEntityByConfigId(ScriptLibContext context, int groupId, int entityType, int configId){
        var entity = context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e.getGroupId() == groupId)
            .filter(e -> e.getEntityType() == entityType)
            .filter(e -> e.getConfigId() == configId)
            .findFirst();

        if(entity.isEmpty()){
            return 1;
        }
        context.getSceneScriptManager().getScene().removeEntity(entity.get());

        return 0;
    }

    public int ShowReminder(ScriptLibContext context, int reminderId) {
        System.out.println("Unimplemented function [ShowRemainder] called. reminderId: " + reminderId);
        return 0;
    }
}
