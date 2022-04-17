package emu.grasscutter.game.dungeons;

import emu.grasscutter.server.game.GameServer;

public class DungeonManager {
	private final GameServer server;
	
	public DungeonManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}
}
