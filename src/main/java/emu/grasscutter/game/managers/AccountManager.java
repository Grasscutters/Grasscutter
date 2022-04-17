package emu.grasscutter.game.managers;

import emu.grasscutter.server.game.GameServer;

public class AccountManager {
	private final GameServer server;
	
	public AccountManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}
}
