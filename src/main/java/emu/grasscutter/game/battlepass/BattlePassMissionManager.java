package emu.grasscutter.game.battlepass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.BattlePassMissionData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.BattlePassMissionRefreshType;
import emu.grasscutter.game.props.BattlePassMissionStatus;
import emu.grasscutter.game.props.WatcherTriggerType;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketBattlePassMissionUpdateNotify;

public class BattlePassMissionManager {
	private final GameServer server;
	private final Map<WatcherTriggerType, List<BattlePassMissionData>> cachedTriggers;
	
	// BP Mission manager for the server, contains cached triggers so we dont have to load it for each player
	public BattlePassMissionManager(GameServer server) {
		this.server = server;
		this.cachedTriggers = new HashMap<>();
		
		for (BattlePassMissionData missionData : GameData.getBattlePassMissionDataMap().values()) {
			if (missionData.isValidRefreshType()) {
				List<BattlePassMissionData> triggerList = getTriggers().computeIfAbsent(missionData.getTriggerType(), e -> new ArrayList<>());
				triggerList.add(missionData);
			}
		}
	}

	public GameServer getServer() {
		return server;
	}
	
	private Map<WatcherTriggerType, List<BattlePassMissionData>> getTriggers() {
		return cachedTriggers;
	}
	
	public void triggerMission(Player player, WatcherTriggerType triggerType) {
		triggerMission(player, triggerType, 0, 1);
	}

	public void triggerMission(Player player, WatcherTriggerType triggerType, int param, int progress) {
		List<BattlePassMissionData> triggerList = getTriggers().get(triggerType);
		
		if (triggerList == null || triggerList.isEmpty()) return;
		
		for (BattlePassMissionData data : triggerList) {
			// Skip params check if param == 0
			if (param != 0) {
				if (!data.getMainParams().contains(param)) {
					continue;
				}
			}
			
			// Get mission from player, if it doesnt exist, then we make one
			BattlePassMission mission = player.getBattlePassManager().loadMissionById(data.getId());
			
			if (mission.isFinshed()) continue;

			// Add progress
			mission.addProgress(progress, data.getProgress());
			
			if (mission.getProgress() >= data.getProgress()) {
				mission.setStatus(BattlePassMissionStatus.MISSION_STATUS_FINISHED);
			}
			
			// Save to db
			player.getBattlePassManager().save();
			
			// Packet
			player.sendPacket(new PacketBattlePassMissionUpdateNotify(mission));
		}
	}
}
