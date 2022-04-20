package emu.grasscutter.game.managers;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
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

	public void sendPrivateMessage(GenshinPlayer player, int targetUid, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}
				
		// Check if command
		if (PREFIXES.contains(message.charAt(0))) {
			CommandMap.getInstance().invoke(player, message);
			return;
		}
		
		// Get target
		GenshinPlayer target = getServer().getPlayerByUid(targetUid);
		
		if (target == null) {
			return;
		}
		
		// Create chat packet
		GenshinPacket packet = new PacketPrivateChatNotify(player.getUid(), target.getUid(), message);
		
		player.sendPacket(packet);
		target.sendPacket(packet);
	}
	
	public void sendPrivateMessage(GenshinPlayer player, int targetUid, int emote) {
		// Get target
		GenshinPlayer target = getServer().getPlayerByUid(targetUid);
		
		if (target == null) {
			return;
		}
		
		// Create chat packet
		GenshinPacket packet = new PacketPrivateChatNotify(player.getUid(), target.getUid(), emote);
		
		player.sendPacket(packet);
		target.sendPacket(packet);
	}
	
	public void sendTeamMessage(GenshinPlayer player, int channel, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}
				
		// Check if command
		if (PREFIXES.contains(message.charAt(0))) {
			CommandMap.getInstance().invoke(player, message);
			return;
		}

		// Create and send chat packet
		player.getWorld().broadcastPacket(new PacketPlayerChatNotify(player, channel, message));
	}

	public void sendTeamMessage(GenshinPlayer player, int channel, int icon) {
		// Create and send chat packet
		player.getWorld().broadcastPacket(new PacketPlayerChatNotify(player, channel, icon));
	}
}
