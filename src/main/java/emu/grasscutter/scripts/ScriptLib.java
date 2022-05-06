package emu.grasscutter.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.packet.send.PacketGadgetStateNotify;
import emu.grasscutter.server.packet.send.PacketWorktopOptionNotify;

public class ScriptLib {
	private final SceneScriptManager sceneScriptManager;
	
	public ScriptLib(SceneScriptManager sceneScriptManager) {
		this.sceneScriptManager = sceneScriptManager;
	}

	public SceneScriptManager getSceneScriptManager() {
		return sceneScriptManager;
	}
	
	public int SetGadgetStateByConfigId(int configId, int gadgetState) {
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
	
	public int DelWorktopOptionByGroupId(int groupId, int configId, int option) {
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
	public int AutoMonsterTide(int challengeIndex, int groupId, int[] config_ids, int param4, int param5, int param6) {
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
				
		if (group == null || group.monsters == null) {
			return 1;
		}
		
		// TODO just spawn all from group for now
		this.getSceneScriptManager().spawnMonstersInGroup(group);
		
		return 0;
	}
	
	public int AddExtraGroupSuite(int groupId, int suite) {
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		
		if (group == null || group.monsters == null) {
			return 1;
		}
		
		// TODO just spawn all from group for now
		this.getSceneScriptManager().spawnMonstersInGroup(group, suite);
		
		return 0;
	}
	
	// param3 (probably time limit for timed dungeons)
	public int ActiveChallenge(int challengeId, int challengeIndex, int param3, int groupId, int objectiveKills, int param5) {
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		
		if (group == null || group.monsters == null) {
			return 1;
		}
		
		DungeonChallenge challenge = new DungeonChallenge(getSceneScriptManager().getScene(), group);
		challenge.setChallengeId(challengeId);
		challenge.setChallengeIndex(challengeIndex);
		challenge.setObjective(objectiveKills);
		
		getSceneScriptManager().getScene().setChallenge(challenge);
		
		challenge.start();
		return 0;
	}
	
	public int GetGroupMonsterCountByGroupId(int groupId) {
		return (int) getSceneScriptManager().getScene().getEntities().values().stream()
								.filter(e -> e instanceof EntityMonster && e.getGroupId() == groupId)
								.count();
	}
	
	public int GetGroupVariableValue(String var) {
		return getSceneScriptManager().getVariables().getOrDefault(var, 0);
	}
	
	public int SetGroupVariableValue(String var, int value) {
		getSceneScriptManager().getVariables().put(var, value);
		return 0;
	}
	
	public LuaValue ChangeGroupVariableValue(String var, int value) {
		getSceneScriptManager().getVariables().put(var, getSceneScriptManager().getVariables().get(var) + value);
		return LuaValue.ZERO;
	}
	
	public int RefreshGroup(LuaTable table) {
		// Kill and Respawn?
		int groupId = table.get("group_id").toint();
		int suite = table.get("suite").toint();
		
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		
		if (group == null || group.monsters == null) {
			return 1;
		}
		
		this.getSceneScriptManager().spawnMonstersInGroup(group, suite);
		this.getSceneScriptManager().spawnGadgetsInGroup(group, suite);
		
		return 0;
	}
	
	public int GetRegionEntityCount(LuaTable table) {
		int regionId = table.get("region_eid").toint();
		int entityType = table.get("entity_type").toint();

		SceneRegion region = this.getSceneScriptManager().getRegionById(regionId);
		
		if (region == null) {
			return 0;
		}

		return (int) region.getEntities().intStream().filter(e -> e >> 24 == entityType).count();
	}
	
	public void PrintContextLog(String msg) {
		Grasscutter.getLogger().info("[LUA] " + msg);
	}

	public int TowerCountTimeStatus(int var1, int var2){
		return 0;
	}
	public int GetGroupMonsterCount(int var1){
		// Maybe...
		return GetGroupMonsterCountByGroupId(var1);
	}
	public int SetMonsterBattleByGroup(int var1, int var2, int var3){
		return 0;
	}

	public int CauseDungeonFail(int var1){
		return 0;
	}
}
