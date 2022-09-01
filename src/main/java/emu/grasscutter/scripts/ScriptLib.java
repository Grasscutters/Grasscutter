package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.SceneData;
import emu.grasscutter.game.dungeons.challenge.DungeonChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.dungeons.challenge.factory.ChallengeFactory;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.game.quest.enums.QuestState;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.server.packet.send.PacketCanUseSkillNotify;
import emu.grasscutter.server.packet.send.PacketDungeonShowReminderNotify;
import emu.grasscutter.server.packet.send.PacketWorktopOptionNotify;
import emu.grasscutter.utils.Position;
import io.netty.util.concurrent.FastThreadLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.jnlua.util.AbstractTableMap;

import java.util.AbstractMap;
import java.util.Optional;

public class ScriptLib {
	public static final Logger logger = LoggerFactory.getLogger(ScriptLib.class);
	private final FastThreadLocal<SceneScriptManager> sceneScriptManager;
	private final FastThreadLocal<SceneGroup> currentGroup;
	public ScriptLib() {
		this.sceneScriptManager = new FastThreadLocal<>();
		this.currentGroup = new FastThreadLocal<>();
	}

	public void setSceneScriptManager(SceneScriptManager sceneScriptManager){
		this.sceneScriptManager.set(sceneScriptManager);
	}

	public void removeSceneScriptManager(){
		this.sceneScriptManager.remove();
	}

	public SceneScriptManager getSceneScriptManager() {
		// normally not null
		return Optional.of(sceneScriptManager.get()).get();
	}

    private String printTable(Object table_){
        AbstractMap table = (AbstractMap) table_;
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(var meta : table.entrySet()) {
			sb.append(meta).append(":").append(table.get(meta)).append(",");
		}
		sb.append("}");
		return sb.toString();
	}
	public void setCurrentGroup(SceneGroup currentGroup){
		this.currentGroup.set(currentGroup);
	}

	public void removeCurrentGroup(){
		this.currentGroup.remove();
	}
	public int SetGadgetStateByConfigId(ScriptLibContext context, int configId, int gadgetState) {
        logger.debug("[LUA] Call SetGadgetStateByConfigId with {},{}",
            configId,gadgetState);
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
		logger.debug("[LUA] Call SetGroupGadgetStateByConfigId with {},{},{}",
				groupId,configId,gadgetState);

		context.getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(e -> e.getGroupId() == groupId)
				.filter(e -> e instanceof EntityGadget)
				.map(e -> (EntityGadget)e)
				.forEach(e -> e.updateState(gadgetState));

		return 0;
	}

	public int SetWorktopOptionsByGroupId(ScriptLibContext context, int groupId, int configId, Object options) {
		logger.debug("[LUA] Call SetWorktopOptionsByGroupId with {},{},{}",
				groupId,configId,options);

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

	public int SetWorktopOptions(ScriptLibContext context, Object table){
		logger.debug("[LUA] Call SetWorktopOptions with {}", printTable(table));
		// TODO
		return 0;
	}
	public int DelWorktopOptionByGroupId(ScriptLibContext context, int groupId, int configId, int option) {
		logger.debug("[LUA] Call DelWorktopOptionByGroupId with {},{},{}",groupId,configId,option);

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
    public int AutoMonsterTide(ScriptLibContext context, int challengeIndex, int groupId, Object ordersConfigId, int tideCount, int sceneLimit, int param6) {
        logger.debug("[LUA] Call AutoMonsterTide with {},{},{},{},{},{}",
            challengeIndex,groupId,ordersConfigId,tideCount,sceneLimit,param6);
        SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);

		if (group == null || group.monsters == null) {
			return 1;
		}

        context.getSceneScriptManager().startMonsterTideInGroup(group, ScriptUtils.toIntegerArray(ordersConfigId), tideCount, sceneLimit);
        return 0;
    }

	public int AddExtraGroupSuite(ScriptLibContext context, int groupId, int suite) {
		logger.debug("[LUA] Call AddExtraGroupSuite with {},{}",
				groupId,suite);
		SceneGroup group = context.getSceneScriptManager().getGroupById(groupId);

		if (group == null || group.monsters == null) {
			return 1;
		}
		var suiteData = group.getSuiteByIndex(suite);
		if(suiteData == null){
			return 1;
		}
		// avoid spawn wrong monster
		if(context.getSceneScriptManager().getScene().getChallenge() != null)
			if(!context.getSceneScriptManager().getScene().getChallenge().inProgress() ||
					context.getSceneScriptManager().getScene().getChallenge().getGroup().id != groupId){
			return 0;
		}
		context.getSceneScriptManager().addGroupSuite(group, suiteData);

		return 0;
	}
	public int GoToGroupSuite(ScriptLibContext context, int groupId, int suite) {
		logger.debug("[LUA] Call GoToGroupSuite with {},{}",
				groupId,suite);
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
		logger.debug("[LUA] Call RemoveExtraGroupSuite with {},{}",
				groupId,suite);

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
		logger.debug("[LUA] Call KillExtraGroupSuite with {},{}",
				groupId,suite);

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
		logger.debug("[LUA] Call ActiveChallenge with {},{},{},{},{},{}",
				challengeId,challengeIndex,timeLimitOrGroupId,groupId,objectiveKills,param5);

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
			dungeonChallenge.setStage(context.getSceneScriptManager().getVariables().getOrDefault("stage", -1) == 0);
		}

		context.getSceneScriptManager().getScene().setChallenge(challenge);
		challenge.start();
		return 0;
	}

	public int GetGroupMonsterCountByGroupId(ScriptLibContext context, int groupId) {
		logger.debug("[LUA] Call GetGroupMonsterCountByGroupId with {}",
				groupId);
		return (int) context.getSceneScriptManager().getScene().getEntities().values().stream()
								.filter(e -> e instanceof EntityMonster && e.getGroupId() == groupId)
								.count();
	}

	public int GetGroupVariableValue(ScriptLibContext context, String var) {
		logger.debug("[LUA] Call GetGroupVariableValue with {}",
				var);
		return context.getSceneScriptManager().getVariables().getOrDefault(var, 0);
	}

	public int SetGroupVariableValue(ScriptLibContext context, String var, int value) {
		logger.debug("[LUA] Call SetGroupVariableValue with {},{}",
				var, value);
		context.getSceneScriptManager().getVariables().put(var, value);
		return 0;
	}

    public int ChangeGroupVariableValue(ScriptLibContext context, String var, int value) {
        logger.debug("[LUA] Call ChangeGroupVariableValue with {},{}",
            var, value);
        context.getSceneScriptManager().getVariables().put(var, context.getSceneScriptManager().getVariables().get(var) + value);
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
		logger.debug("[LUA] Call RefreshGroup with {}",
				printTable(table));

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
		logger.debug("[LUA] Call GetRegionEntityCount with {}",
				printTable(table));

		var region = context.getSceneScriptManager().getRegionById(regionId);

		if (region == null) {
			return 0;
		}

		return (int) region.getEntities().stream().filter(e -> e >> 24 == entityType).count();
	}

	public void PrintContextLog(ScriptLibContext context, String msg) {
		logger.info("[LUA] " + msg);
	}

	public int TowerCountTimeStatus(ScriptLibContext context, int isDone){
		logger.debug("[LUA] Call TowerCountTimeStatus with {}",
				isDone);
		// TODO record time
		return 0;
	}
	public int GetGroupMonsterCount(ScriptLibContext context){
		logger.debug("[LUA] Call GetGroupMonsterCount ");

        return (int) context.getSceneScriptManager().getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityMonster &&
                e.getGroupId() == context.getCurrentGroup().id)
            .count();
	}
	public int SetMonsterBattleByGroup(ScriptLibContext context, int var1, int var2){
		logger.debug("[LUA] Call SetMonsterBattleByGroup with {},{}",
				var1,var2);
		// TODO
		return 0;
	}

	public int CauseDungeonFail(ScriptLibContext context){
		logger.debug("[LUA] Call CauseDungeonFail.");

		return 0;
	}

	public int GetGroupVariableValueByGroup(ScriptLibContext context, String name, int groupId){
		logger.debug("[LUA] Call GetGroupVariableValueByGroup with {},{}",
				name,groupId);

		return context.getSceneScriptManager().getVariables().getOrDefault(name, 0);
	}

    public int SetIsAllowUseSkill(ScriptLibContext context, int canUse) {
        logger.debug("[LUA] Call SetIsAllowUseSkill with {}",
            canUse);

        context.getSceneScriptManager().getScene().broadcastPacket(new PacketCanUseSkillNotify(canUse == 1));
        return 0;
    }

    public int KillEntityByConfigId(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var configId = table.get("config_id");
		logger.debug("[LUA] Call KillEntityByConfigId with {}",
				printTable(table));
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
		logger.debug("[LUA] Call SetGroupVariableValueByGroup with {},{},{}",
				key,value,groupId);

		context.getSceneScriptManager().getVariables().put(key, value);
		return 0;
	}

    public int CreateMonster(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var configId = ScriptUtils.getInt(table.get("config_id"));
        var delayTime = ScriptUtils.getInt(table.get("delay_time"));
		logger.debug("[LUA] Call CreateMonster with {}",
				printTable(table));

		if(context.getCurrentGroup() == null){
			return 1;
		}

		context.getSceneScriptManager().spawnMonstersByConfigId(context.getCurrentGroup(), configId, delayTime);
		return 0;
	}

	public int TowerMirrorTeamSetUp(ScriptLibContext context, int team) {
		logger.debug("[LUA] Call TowerMirrorTeamSetUp with {},{}",
				team);

		context.getSceneScriptManager().unloadCurrentMonsterTide();
		context.getSceneScriptManager().getScene().getPlayers().get(0).getTowerManager().mirrorTeamSetUp(team-1);

		return 0;
	}

    public int CreateGadget(ScriptLibContext context, Object table_) throws Exception {
        var table = (AbstractTableMap)table_;
        var configId = ScriptUtils.getInt(table.get("config_id"));
		logger.debug("[LUA] Call CreateGadget with {}",
				printTable(table));

		var group = context.getCurrentGroup();

        if (group == null) {
            return 1;
        }

		var gadget = group.gadgets.get(configId);
		var entity = context.getSceneScriptManager().createGadget(group.id, group.block_id, gadget);
        /* where does this come from?
        if(entity == null) {
            logger.debug("Failed to create a gadget on group: {}, block: {} configId: {} (Maybe there is a duplication.)", group.id, group.block_id, configId);
            return 0;
        }*/
		context.getSceneScriptManager().addEntity(entity);

		return 0;
	}
    public int CheckRemainGadgetCountByGroupId(ScriptLibContext context, Object table_){
        var table = (AbstractTableMap)table_;
        var groupId = ScriptUtils.getInt(table.get("group_id"));
		logger.debug("[LUA] Call CheckRemainGadgetCountByGroupId with {}",
				printTable(table));

		var count = context.getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == groupId)
				.count();
		return (int)count;
	}

	public int GetGadgetStateByConfigId(ScriptLibContext context, int groupId, int configId){
		logger.debug("[LUA] Call GetGadgetStateByConfigId with {},{}",
				groupId, configId);

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
		logger.debug("[LUA] Call MarkPlayerAction with {},{},{}",
				var2,var3,var4);
        //TODO
		return 0;
	}

	public int AddQuestProgress(ScriptLibContext context, String var1){
		logger.debug("[LUA] Call AddQuestProgress with {}",
				var1);

        for(var player : context.getSceneScriptManager().getScene().getPlayers()){
            player.getQuestManager().triggerEvent(QuestTrigger.QUEST_COND_LUA_NOTIFY, var1,0,0,0,0);
            player.getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_LUA_NOTIFY, var1); //missing params[1], paramStr and count
        }

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

    public int GetEntityType(ScriptLibContext context, int entityId){
        var entity = context.getSceneScriptManager().getScene().getEntityById(entityId);
        if(entity == null){
            return EntityType.None.getValue();
        }

        return entity.getEntityType();
    }
    //Not fully implemented. But most of the usage of this function rely on EntityType.AVATAR.
    public int GetEntityType(int tid) {
        logger.debug("[LUA] Call GetEntityType with {}",
            tid);
        return EntityType.Avatar.getValue();
    }

    public int GetQuestState(ScriptLibContext context, int entityId, int questId){
        logger.debug("[LUA] Call GetQuestState for entity {} and quest {}",
            entityId, questId);
        var player = context.getSceneScriptManager().getScene().getWorld().getHost();
        var quest = player.getQuestManager().getQuestById(questId);
        if(quest == null){
            return QuestState.QUEST_STATE_NONE.getValue();
        }

        return quest.getState().getValue();
    }

    public int ShowReminder(ScriptLibContext context, int reminderId){
        context.getSceneScriptManager().getScene().broadcastPacket(new PacketDungeonShowReminderNotify(reminderId));
        return 0;
    }

    public int ShowReminderRadius(ScriptLibContext context, int reminderID, Position location, int var4){
        logger.warn("[LUA] Call ShowReminderRadius {} {} {}", reminderID, location,var4);
        //TODO
        // var4 == distance?
        // e.g. scene3_group133002098.lua action_EVENT_ENTER_REGION_98007 line 247
        return 0;
    }
    public int BeginCameraSceneLook(ScriptLibContext context, int var2){
        logger.warn("[LUA] Call BeginCameraSceneLook {} {} {} {}", var2);
        //TODO
        // var2 == cameraSceneProperties
        // cameraSceneProperties:
        // look_pos: Position
        // is_allow_input: bool
        // duration: float
        // is_force: bool
        // is_broadcast: bool
        // is_recover_keep_current: bool
        // delay: int
        // is_set_follow_pos: bool
        // follow_pos: Position
        // is_force_walk: bool
        // is_change_play_mode: bool
        // is_set_screen_XY: bool
        // screen_x int
        // screen_y int
        // e.g. scene3_group133002098.lua action_EVENT_ENTER_REGION_98007 line 247
        return 0;
    }
    public int SetPlatformRouteId(ScriptLibContext context, int var2, int routeId){
        logger.warn("[LUA] Call SetPlatformRouteId {} {}", var2, routeId);
        //TODO
        // var2 == seelie ID?
        // e.g. scene3_group133003381.lua action_EVENT_ENTER_REGION_381007 line 387
        // or scene3_group133001159.lua action_EVENT_ENTER_REGION_159007 line 387
        return 0;
    }
    public int CreateGroupTimerEvent(ScriptLibContext context, int unk, String unk2, int unk3){
        logger.warn("[LUA] Call CreateGroupTimerEvent {} {} {}", unk, unk2, unk3);
        //TODO
        // e.g. scene3_group133003002.lua action_EVENT_ANY_MONSTER_LIVE_529 line 177
        // maybe groupID, timer type/abilityTimer?, time in sec?
        return 0;
    }
    public int GetDummyPoint(SceneData sceneData, int unk2, int unk3){
        logger.warn("[LUA] Call GetDummyPoint {} {} {}", sceneData, unk2, unk3);
        //TODO
        // e.g. Q358ClientConfig.lua action_EVENT_ANY_MONSTER_LIVE_529 line 177
        // maybe groupID, timer type/abilityTimer?, time in sec?
        return 0;
    }
    public int SetGroupReplaceable(ScriptLibContext context, int unk, boolean unk2){
        logger.warn("[LUA] Call SetGroupReplaceable {} {}", unk, unk2);
        //TODO
        // e.g. scene3_group133003136.lua action_EVENT_ANY_MONSTER_DIE_511 line 445
        // maybe groupID, timer type/abilityTimer?, time in sec?
        return 0;
    }
    public int PrintLog(ScriptLibContext context, String message){
        logger.info("[LUA] PrintLog: {}", message);
        return 0;
    }

    public int RemoveEntityByConfigId(ScriptLibContext context, int groupId, int entityType, int configId) {
        logger.debug("[LUA] Call RemoveEntityByConfigId");

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
}
