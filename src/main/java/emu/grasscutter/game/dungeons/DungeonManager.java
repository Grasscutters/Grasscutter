package emu.grasscutter.game.dungeons;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.custom.ScenePointEntry;
import emu.grasscutter.data.def.DungeonData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketDungeonEntryInfoRsp;
import emu.grasscutter.server.packet.send.PacketPlayerEnterDungeonRsp;
import emu.grasscutter.utils.Position;

public class DungeonManager {
	private final GameServer server;
	
	public DungeonManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}

	public void getEntryInfo(Player player, int pointId) {
		ScenePointEntry entry = GameData.getScenePointEntryById(player.getScene().getId(), pointId);
		
		if (entry == null) {
			// Error
			player.sendPacket(new PacketDungeonEntryInfoRsp());
			return;
		}
		
		player.sendPacket(new PacketDungeonEntryInfoRsp(player, entry.getPointData()));
	}

	public boolean enterDungeon(Player player, int pointId, int dungeonId) {
		DungeonData data = GameData.getDungeonDataMap().get(dungeonId);
		
		if (data == null) {
			return false;
		}
		
		Grasscutter.getLogger().info(player.getNickname() + " is trying to enter dungeon " + dungeonId);
		
		int sceneId = data.getSceneId();
		player.getScene().setPrevScene(sceneId);
		
		player.getWorld().transferPlayerToScene(player, sceneId, data);
		
		player.getScene().setPrevScenePoint(pointId);
		player.sendPacket(new PacketPlayerEnterDungeonRsp(pointId, dungeonId));
		return true;
	}
	
	public void exitDungeon(Player player) {
		if (player.getScene().getSceneType() != SceneType.SCENE_DUNGEON) {
			return;
		}
		
		// Get previous scene
		int prevScene = player.getScene().getPrevScene() > 0 ? player.getScene().getPrevScene() : 3;
		
		// Get previous position
		DungeonData dungeonData = player.getScene().getDungeonData();
		Position prevPos = new Position(GameConstants.START_POSITION);
		
		if (dungeonData != null) {
			ScenePointEntry entry = GameData.getScenePointEntryById(prevScene, player.getScene().getPrevScenePoint());
			
			if (entry != null) {
				prevPos.set(entry.getPointData().getTranPos());
			}
		}

		// Transfer player back to world
		player.getWorld().transferPlayerToScene(player, prevScene, prevPos);
		player.sendPacket(new BasePacket(PacketOpcodes.PlayerQuitDungeonRsp));
	}
	
	public void updateDailyDungeons() {
		for (ScenePointEntry entry : GameData.getScenePointEntries().values()) {
			entry.getPointData().updateDailyDungeon();
		}
	}
}
