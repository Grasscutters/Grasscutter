package emu.grasscutter.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.luaj.vm2.LuaTable;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneMonster;
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
		List<GameEntity> toAdd = new ArrayList<>();
		
		for (SceneMonster monster : group.monsters) {
			MonsterData data = GameData.getMonsterDataMap().get(monster.monster_id);
			
			if (data == null) {
				continue;
			}
			
			EntityMonster entity = new EntityMonster(getSceneScriptManager().getScene(), data, monster.pos, monster.level);
			entity.getRotation().set(monster.rot);
			entity.setGroupId(group.id);
			entity.setConfigId(monster.config_id);
			
			toAdd.add(entity);
		}
		
		if (toAdd.size() > 0) {
			getSceneScriptManager().getScene().addEntities(toAdd);
			
			for (GameEntity entity : toAdd) {
				this.getSceneScriptManager().onMonsterSpawn((EntityMonster) entity);
			}
		}
		
		return 0;
	}
	
	public int ActiveChallenge(int challengeId, int challengeIndex, int param3, int groupId, int param4, int param5) {
		SceneGroup group = getSceneScriptManager().getGroupById(groupId);
		
		if (group == null || group.monsters == null) {
			return 1;
		}
		
		DungeonChallenge challenge = new DungeonChallenge(getSceneScriptManager().getScene(), group);
		challenge.setChallengeId(challengeId);
		challenge.setChallengeIndex(challengeIndex);
		
		getSceneScriptManager().getScene().setChallenge(challenge);
		
		challenge.start();
		return 0;
	}
	
	public int RefreshGroup(LuaTable table) {
		// Add group back to suite
		return 0;
	}
	
	public void PrintContextLog(String msg) {
		Grasscutter.getLogger().info("[LUA] " + msg);
	}
}
