package emu.grasscutter.game.managers;

import emu.grasscutter.commands.PlayerCommands;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketPlayerChatNotify;
import emu.grasscutter.server.packet.send.PacketPrivateChatNotify;

public class ChatManager {
	private final GameServer server;
	
	public ChatManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}

	public void sendPrivChat(GenshinPlayer player, int targetUid, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}
				
		// Check if command
		if (message.charAt(0) == '!') {
			PlayerCommands.handle(player, message);
			return;
		}
		
		// Get target
		GenshinPlayer target = getServer().getPlayerById(targetUid);
		
		if (target == null) {
			return;
		}
		
		// Create chat packet
		GenshinPacket packet = new PacketPrivateChatNotify(player.getId(), target.getId(), message);
		
		player.sendPacket(packet);
		target.sendPacket(packet);
	}
	
	public void sendPrivChat(GenshinPlayer player, int targetUid, int emote) {
		// Get target
		GenshinPlayer target = getServer().getPlayerById(targetUid);
		
		if (target == null) {
			return;
		}
		
		// Create chat packet
		GenshinPacket packet = new PacketPrivateChatNotify(player.getId(), target.getId(), emote);
		
		player.sendPacket(packet);
		target.sendPacket(packet);
	}
	
	public void sendTeamChat(GenshinPlayer player, int channel, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}
				
		// Check if command
		if (message.charAt(0) == '!') {
			PlayerCommands.handle(player, message);
			return;
		}

		// Create and send chat packet
		player.getWorld().broadcastPacket(new PacketPlayerChatNotify(player, channel, message));
	}

	public void sendTeamChat(GenshinPlayer player, int channel, int icon) {
		// Create and send chat packet
		player.getWorld().broadcastPacket(new PacketPlayerChatNotify(player, channel, icon));
	}
}
