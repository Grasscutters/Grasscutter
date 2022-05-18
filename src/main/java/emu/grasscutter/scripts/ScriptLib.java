package emu.grasscutter.scripts;

import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.server.packet.send.PacketCanUseSkillNotify;
import emu.grasscutter.server.packet.send.PacketGadgetStateNotify;
import emu.grasscutter.server.packet.send.PacketWorktopOptionNotify;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ScriptLib {
	public static final Logger logger = LoggerFactory.getLogger(ScriptLib.class);
	private final SceneScriptManager sceneScriptManager;
	
	public ScriptLib(SceneScriptManager sceneScriptManager) {
		this.sceneScriptManager = sceneScriptManager;
		this.currentGroup = new ThreadLocal<>();
	}

	public SceneScriptManager getSceneScriptManager() {
		return sceneScriptManager;
	}

	private String printTable(LuaTable table){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(var meta : table.keys()){
			sb.append(meta).append(":").append(table.get(meta)).append(",");
		}
		sb.append("}");
		return sb.toString();
	}
	private final ThreadLocal<SceneGroup> currentGroup;
	public void setCurrentGroup(SceneGroup currentGroup){
		logger.debug("current {}", currentGroup);
		this.currentGroup.set(currentGroup);
	}
	public Optional<SceneGroup> getCurrentGroup(){
		return Optional.ofNullable(this.currentGroup.get());
	}
	public void removeCurrentGroup(){
		this.currentGroup.remove();
	}
	public int SetGadgetStateByConfigId(int configId, int gadgetState) {
		logger.debug("[LUA] Call SetGadgetStateByConfigId with {},{}",
				configId,gadgetState);
		Optional<GameEntity> entity = getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(e -> e.getConfigId() == configId).findFirst();

		if (entity.isEmpty()) {
			return 1;
		}
		
		if (!(entity.get() instanceof EntityGadget)) {
			return 1;
		}
		
		EntityGadget gadget = (EntityGadget) entity.get();
		gadget.setState(gadgetState);
		
		getSceneScriptManager().getScene().broadcastPacket(new PacketGadgetStateNotify(gadget, gadgetState));
		return 0;
	}

	public int SetGroupGadgetStateByConfigId(int groupId, int configId, int gadgetState) {
		logger.debug("[LUA] Call SetGroupGadgetStateByConfigId with {},{},{}",
				groupId,configId,gadgetState);
		List<GameEntity> list = getSceneScriptManager().getScene().getEntities().values().stream()
												.filter(e -> e.getGroupId() == groupId).toList();
		
		for (GameEntity entity : list) {
			if (!(entity instanceof EntityGadget)) {
				continue;
			}
			
			EntityGadget gadget = (EntityGadget) entity;
			gadget.setState(gadgetState);
			
			getSceneScriptManager().getScene().broadcastPacket(new PacketGadgetStateNotify(gadget, gadgetState));
		}
		
		return 0;
	}
	
	public int SetWorktopOptionsByGroupId(int groupId, int configId, int[] options) {
		logger.debug("[LUA] Call SetWorktopOptionsByGroupId with {},{},{}",
				groupId,configId,options);
		Optional<GameEntity> entity = getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(e -> e.getConfigId() == configId && e.getGroupId() == groupId).findFirst();

		if (entity.isEmpty()) {
			return 1;
		}
		
		if (!(entity.get() instanceof EntityGadget)) {
			return 1;
		}
		
		EntityGadget gadget = (EntityGadget) entity.get();
		gadget.addWorktopOptions(options);

		getSceneScriptManager().getScene().broadcastPacket(new PacketWorktopOptionNotify(gadget));
		return 0;
	}

	public int SetWorktopOptions(LuaTable table){
		logger.debug("[LUA] Call SetWorktopOptions with {}", printTable(table));
		// TODO
		return 0;
	}
	public int DelWorktopOptionByGroupId(int groupId, int configId, int option) {
		logger.debug("[LUA] Call DelWorktopOptionByGroupId with {},{},{}",groupId,configId,option);

		Optional<GameEntity> entity = getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(e -> e.getConfigId() == configId && e.getGroupId() == groupId).findFirst();

		if (entity.isEmpty()) {
			return 1;
		}
		
		if (!(entity.get() instanceof EntityGadget)) {
			return 1;
		}
		
		EntityGadget gadget = (EntityGadget) entity.get();
		gadget.removeWorktopOption(option);
		
		getSceneScriptManager().getScene().broadcastPacket(new PacketWorktopOptionNotify(gadget));
		return 0;
	}
	
	// Some fields are guessed
	public int AutoMonsterTide(int challengeIndex, int groupId, Integer[] ordersConfigId, int tideCount, int sceneLimit, int param6) {
		logger.debug("[LUA] Call AutoMonsterTide with {},{},{},{},{},{}",
				challengeIndex,groupId,ordersConfigId,tideCount,sceneLimit,param6);

		SceneGroup group = getSceneScriptManager().getGroupById(groupId);

		if (group == null || group.monsters == null) {
			return 1;
		}

		this.getSceneScriptManager().startMonsterTideInGroup(group, ordersConfigId, tideCount, sceneLimit);
		
		return 0;
	}
	
	public int AddExtraGroupSuite(int groupId, int suite) {
		logger.debug("[LUA] Call AddExtraGroupSuite with {},{}",
				groupId,suite);
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		
		if (group == null || group.monsters == null) {
			return 1;
		}

		// avoid spawn wrong monster
		if(getSceneScriptManager().getScene().getChallenge() != null)
			if(!getSceneScriptManager().getScene().getChallenge().inProgress() ||
					getSceneScriptManager().getScene().getChallenge().getGroup().id != groupId){
			return 0;
		}
		this.getSceneScriptManager().spawnMonstersInGroup(group, suite);
		
		return 0;
	}
	
	// param3 (probably time limit for timed dungeons)
	public int ActiveChallenge(int challengeId, int challengeIndex, int timeLimitOrGroupId, int groupId, int objectiveKills, int param5) {
		logger.debug("[LUA] Call ActiveChallenge with {},{},{},{},{},{}",
				challengeId,challengeIndex,timeLimitOrGroupId,groupId,objectiveKills,param5);

		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		var objective = objectiveKills;

		if(group == null){
			group = getSceneScriptManager().getGroupById(timeLimitOrGroupId);
			objective = groupId;
		}
		
		if (group == null || group.monsters == null) {
			return 1;
		}

		if(getSceneScriptManager().getScene().getChallenge() != null &&
				getSceneScriptManager().getScene().getChallenge().inProgress())
		{
			return 0;
		}

		DungeonChallenge challenge = new DungeonChallenge(getSceneScriptManager().getScene(),
				group, challengeId, challengeIndex, objective);
		// set if tower first stage (6-1)
		challenge.setStage(getSceneScriptManager().getVariables().getOrDefault("stage", -1) == 0);

		getSceneScriptManager().getScene().setChallenge(challenge);

		challenge.start();
		return 0;
	}
	
	public int GetGroupMonsterCountByGroupId(int groupId) {
		logger.debug("[LUA] Call GetGroupMonsterCountByGroupId with {}",
				groupId);
		return (int) getSceneScriptManager().getScene().getEntities().values().stream()
								.filter(e -> e instanceof EntityMonster && e.getGroupId() == groupId)
								.count();
	}
	
	public int GetGroupVariableValue(String var) {
		logger.debug("[LUA] Call GetGroupVariableValue with {}",
				var);
		return getSceneScriptManager().getVariables().getOrDefault(var, 0);
	}
	
	public int SetGroupVariableValue(String var, int value) {
		logger.debug("[LUA] Call SetGroupVariableValue with {},{}",
				var, value);
		getSceneScriptManager().getVariables().put(var, value);
		return 0;
	}
	
	public LuaValue ChangeGroupVariableValue(String var, int value) {
		logger.debug("[LUA] Call ChangeGroupVariableValue with {},{}",
				var, value);

		getSceneScriptManager().getVariables().put(var, getSceneScriptManager().getVariables().get(var) + value);
		return LuaValue.ZERO;
	}

	/**
	 * Set the actions and triggers to designated group
	 */
	public int RefreshGroup(LuaTable table) {
		logger.debug("[LUA] Call RefreshGroup with {}",
				printTable(table));
		// Kill and Respawn?
		int groupId = table.get("group_id").toint();
		int suite = table.get("suite").toint();
		
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		
		if (group == null || group.monsters == null) {
			return 1;
		}
		
		getSceneScriptManager().refreshGroup(group, suite);
		
		return 0;
	}
	
	public int GetRegionEntityCount(LuaTable table) {
		logger.debug("[LUA] Call GetRegionEntityCount with {}",
				table);
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

	public int TowerCountTimeStatus(int isDone, int var2){
		logger.debug("[LUA] Call TowerCountTimeStatus with {},{}",
				isDone,var2);
		// TODO record time
		return 0;
	}
	public int GetGroupMonsterCount(int var1){
		logger.debug("[LUA] Call GetGroupMonsterCount with {}",
				var1);

		return (int) getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(e -> e instanceof EntityMonster &&
						e.getGroupId() == getCurrentGroup().map(sceneGroup -> sceneGroup.id).orElse(-1))
				.count();
	}
	public int SetMonsterBattleByGroup(int var1, int var2, int var3){
		logger.debug("[LUA] Call SetMonsterBattleByGroup with {},{},{}",
				var1,var2,var3);
		// TODO
		return 0;
	}

	public int CauseDungeonFail(int var1){
		logger.debug("[LUA] Call CauseDungeonFail with {}",
				var1);

		return 0;
	}

	public int GetGroupVariableValueByGroup(String name, int groupId){
		logger.debug("[LUA] Call GetGroupVariableValueByGroup with {},{}",
				name,groupId);

		return getSceneScriptManager().getVariables().getOrDefault(name, 0);
	}

	public int SetIsAllowUseSkill(int canUse, int var2){
		logger.debug("[LUA] Call SetIsAllowUseSkill with {},{}",
				canUse,var2);

		getSceneScriptManager().getScene().broadcastPacket(new PacketCanUseSkillNotify(canUse == 1));
		return 0;
	}

	public int KillEntityByConfigId(LuaTable table){
		logger.debug("[LUA] Call KillEntityByConfigId with {}",
				printTable(table));
		var configId = table.get("config_id");
		if(configId == LuaValue.NIL){
			return 1;
		}

		var entity = getSceneScriptManager().getScene().getEntityByConfigId(configId.toint());
		if(entity == null){
			return 1;
		}
		getSceneScriptManager().getScene().killEntity(entity, 0);
		return 0;
	}

	public int SetGroupVariableValueByGroup(String key, int value, int groupId){
		logger.debug("[LUA] Call SetGroupVariableValueByGroup with {},{},{}",
				key,value,groupId);

		getSceneScriptManager().getVariables().put(key, value);
		return 0;
	}

	public int CreateMonster(LuaTable table){
		logger.debug("[LUA] Call CreateMonster with {}",
				printTable(table));
		var configId = table.get("config_id").toint();
		var delayTime = table.get("delay_time").toint();

		if(getCurrentGroup().isEmpty()){
			return 1;
		}

		getSceneScriptManager().spawnMonstersByConfigId(getCurrentGroup().get(), configId, delayTime);
		return 0;
	}

	public int TowerMirrorTeamSetUp(int team, int var1) {
		logger.debug("[LUA] Call TowerMirrorTeamSetUp with {},{}",
				team,var1);

		getSceneScriptManager().unloadCurrentMonsterTide();
		getSceneScriptManager().getScene().getPlayers().get(0).getTowerManager().mirrorTeamSetUp(team-1);

		return 0;
	}

	public int CreateGadget(LuaTable table){
		logger.debug("[LUA] Call CreateGadget with {}",
				printTable(table));
		var configId = table.get("config_id").toint();

		var group = getCurrentGroup();
		if(group.isEmpty()){
			return 1;
		}
		var gadget = group.get().gadgets.get(configId);
		var entity = getSceneScriptManager().createGadget(group.get().id, group.get().block_id, gadget);
		getSceneScriptManager().addEntity(entity);

		return 0;
	}
	public int CheckRemainGadgetCountByGroupId(LuaTable table){
		logger.debug("[LUA] Call CheckRemainGadgetCountByGroupId with {}",
				printTable(table));
		var groupId = table.get("group_id").toint();

		var count = getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == groupId)
				.count();
		return (int)count;
	}

	public int GetGadgetStateByConfigId(int groupId, int configId){
		logger.debug("[LUA] Call GetGadgetStateByConfigId with {},{}",
				groupId, configId);
		var gadget = getSceneScriptManager().getScene().getEntities().values().stream()
				.filter(g -> g instanceof EntityGadget entityGadget && entityGadget.getGroupId() == groupId)
				.filter(g -> g.getConfigId() == configId)
				.findFirst();
		if(gadget.isEmpty()){
			return 1;
		}
		return ((EntityGadget)gadget.get()).getState();
	}

	public int MarkPlayerAction(int var1, int var2, int var3, int var4){
		logger.debug("[LUA] Call MarkPlayerAction with {},{}",
				var1, var2);

		return 0;
	}

	public int AddQuestProgress(String var1){
		logger.debug("[LUA] Call AddQuestProgress with {}",
				var1);

		return 0;
	}

	/**
	 * change the state of gadget
	 */
	public int ChangeGroupGadget(LuaTable table){
		logger.debug("[LUA] Call ChangeGroupGadget with {}",
				printTable(table));
		var configId = table.get("config_id").toint();
		var state = table.get("state").toint();

		var entity = getSceneScriptManager().getScene().getEntityByConfigId(configId);
		if(entity == null){
			return 1;
		}

		if(entity instanceof EntityGadget entityGadget){
			getSceneScriptManager().getScene().updateGadgetState(entityGadget, state);
		}

		return 0;
	}
}
