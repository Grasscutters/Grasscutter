package emu.grasscutter.game.shop;

import emu.grasscutter.server.game.GameServer;

public class ShopManager {
	private final GameServer server;
	
	public ShopManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}
}
