package emu.grasscutter.game.managers;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketPlayerChatNotify;
import emu.grasscutter.server.packet.send.PacketPrivateChatNotify;

import java.util.Arrays;
import java.util.List;

public class ChatManager {
	static final List<Character> PREFIXES = Arrays.asList('/', '!');
	
	private final GameServer server;
	
	public ChatManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}

	public void sendPrivateMessage(Player player, int targetUid, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}
		
		// Get target
		Player target = getServer().getPlayerByUid(targetUid);
				
		// Check if command
		if (PREFIXES.contains(message.charAt(0))) {
			CommandMap.getInstance().invoke(player, target, message.substring(1));
			return;
		}
		
		if (target == null) {
			return;
		}
		
		// Create chat packet
		BasePacket packet = new PacketPrivateChatNotify(player.getUid(), target.getUid(), message);
		
		player.sendPacket(packet);
		target.sendPacket(packet);
	}
	
	public void sendPrivateMessage(Player player, int targetUid, int emote) {
		// Get target
		Player target = getServer().getPlayerByUid(targetUid);
		
		if (target == null) {
			return;
		}
		
		// Create chat packet
		BasePacket packet = new PacketPrivateChatNotify(player.getUid(), target.getUid(), emote);
		
		player.sendPacket(packet);
		target.sendPacket(packet);
	}
	
	public void sendTeamMessage(Player player, int channel, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}
				
		// Check if command
		if (PREFIXES.contains(message.charAt(0))) {
			CommandMap.getInstance().invoke(player, null, message);
			return;
		}

		// Create and send chat packet
		player.getWorld().broadcastPacket(new PacketPlayerChatNotify(player, channel, message));
	}

	public void sendTeamMessage(Player player, int channel, int icon) {
		// Create and send chat packet
		player.getWorld().broadcastPacket(new PacketPlayerChatNotify(player, channel, icon));
	}
}
