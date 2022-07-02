package emu.grasscutter.game.managers.chat;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketPlayerChatNotify;
import emu.grasscutter.server.packet.send.PacketPrivateChatNotify;

import java.util.regex.Pattern;

public class ChatManager implements ChatManagerHandler {
	static final String PREFIXES = "[/!]";
	static final Pattern RE_PREFIXES = Pattern.compile(PREFIXES);
	static final Pattern RE_COMMANDS = Pattern.compile("\n" + PREFIXES);

	private final GameServer server;

	public ChatManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}

	private boolean tryInvokeCommand(Player sender, Player target, String rawMessage) {
		if (!RE_PREFIXES.matcher(rawMessage.substring(0, 1)).matches())
			return false;
		for (String line : rawMessage.substring(1).split("\n[/!]"))
			CommandMap.getInstance().invoke(sender, target, line);
		return true;
	}

	public void sendPrivateMessage(Player player, int targetUid, String message) {
		// Sanity checks
		if (message == null || message.length() == 0) {
			return;
		}

		// Get target
		Player target = getServer().getPlayerByUid(targetUid);

		// Check if command
		if (tryInvokeCommand(player, target, message)) {
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
		if (tryInvokeCommand(player, null, message)) {
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
