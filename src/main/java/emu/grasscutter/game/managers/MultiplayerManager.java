package emu.grasscutter.game.managers;

import emu.grasscutter.game.CoopRequest;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.GenshinPlayer.SceneLoadState;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.net.proto.PlayerApplyEnterMpReasonOuterClass.PlayerApplyEnterMpReason;
import emu.grasscutter.game.World;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.PacketPlayerApplyEnterMpNotify;
import emu.grasscutter.server.packet.send.PacketPlayerApplyEnterMpResultNotify;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;

public class MultiplayerManager {
	private final GameServer server;
	
	public MultiplayerManager(GameServer server) {
		this.server = server;
	}

	public GameServer getServer() {
		return server;
	}

	public void applyEnterMp(GenshinPlayer player, int targetUid) {
		GenshinPlayer target = getServer().getPlayerByUid(targetUid);
		if (target == null) {
			player.sendPacket(new PacketPlayerApplyEnterMpResultNotify(targetUid, "", false, PlayerApplyEnterMpReason.PlayerCannotEnterMp));
			return;
		}
		
		// Sanity checks - Dont let player join if already in multiplayer
		if (player.getWorld().isMultiplayer()) {
			return;
		}
		
		/*
		if (target.getWorld().isDungeon()) {
			player.sendPacket(new PacketPlayerApplyEnterMpResultNotify(targetUid, "", false, PlayerApplyEnterMpReason.SceneCannotEnter));
			return;
		}
		*/
		
		// Get request
		CoopRequest request = target.getCoopRequests().get(player.getUid());
		
		if (request != null && !request.isExpired()) {
			// Join request already exists
			return;
		}
		
		// Put request in
		request = new CoopRequest(player);
		target.getCoopRequests().put(player.getUid(), request);
		
		// Packet
		target.sendPacket(new PacketPlayerApplyEnterMpNotify(player));
	}

	public void applyEnterMpReply(GenshinPlayer hostPlayer, int applyUid, boolean isAgreed) {
		// Checks
		CoopRequest request = hostPlayer.getCoopRequests().get(applyUid);
		if (request == null || request.isExpired()) {
			return;
		}
		
		// Remove now that we are handling it
		GenshinPlayer requester = request.getRequester();
		hostPlayer.getCoopRequests().remove(applyUid);
		
		// Sanity checks - Dont let the requesting player join if they are already in multiplayer
		if (requester.getWorld().isMultiplayer()) {
			request.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(hostPlayer, false, PlayerApplyEnterMpReason.PlayerCannotEnterMp));
			return;
		}
		
		// Response packet
		request.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(hostPlayer, isAgreed, PlayerApplyEnterMpReason.PlayerJudge));
		
		// Declined
		if (!isAgreed) {
			return;
		}

		// Success
		if (!hostPlayer.getWorld().isMultiplayer()) {
			// Player not in multiplayer, create multiplayer world
			World world = new World(hostPlayer, true);

			// Add
			world.addPlayer(hostPlayer);
			
			// Rejoin packet
			hostPlayer.sendPacket(new PacketPlayerEnterSceneNotify(hostPlayer, hostPlayer, EnterType.EnterSelf, EnterReason.HostFromSingleToMp, hostPlayer.getScene().getId(), hostPlayer.getPos()));
		}
		
		// Set scene pos and id of requester to the host player's
		requester.getPos().set(hostPlayer.getPos());
		requester.getRotation().set(hostPlayer.getRotation());
		requester.setSceneId(hostPlayer.getSceneId());
		
		// Make requester join
		hostPlayer.getWorld().addPlayer(requester);

		// Packet
		requester.sendPacket(new PacketPlayerEnterSceneNotify(requester, hostPlayer, EnterType.EnterOther, EnterReason.TeamJoin, hostPlayer.getScene().getId(), hostPlayer.getPos()));
	}
	
	public boolean leaveCoop(GenshinPlayer player) {
		// Make sure player's world is multiplayer
		if (!player.getWorld().isMultiplayer()) {
			return false;
		}
		
		// Make sure everyone's scene is loaded
		for (GenshinPlayer p : player.getWorld().getPlayers()) {
			if (p.getSceneLoadState() != SceneLoadState.LOADED) {
				return false;
			}
		}
		
		// Create new world for player
		World world = new World(player);
		world.addPlayer(player);
	
		// Packet
		player.sendPacket(new PacketPlayerEnterSceneNotify(player, EnterType.EnterSelf, EnterReason.TeamBack, player.getScene().getId(), player.getPos()));
		
		return true;
	}

	public boolean kickPlayer(GenshinPlayer player, int targetUid) {
		// Make sure player's world is multiplayer and that player is owner
		if (!player.getWorld().isMultiplayer() || player.getWorld().getHost() != player) {
			return false;
		}
		
		// Get victim and sanity checks
		GenshinPlayer victim = player.getServer().getPlayerByUid(targetUid);
		
		if (victim == null || victim == player) {
			return false;
		}
		
		// Make sure victim's scene has loaded
		if (victim.getSceneLoadState() != SceneLoadState.LOADED) {
			return false;
		}
		
		// Kick
		World world = new World(victim);
		world.addPlayer(victim);
		
		victim.sendPacket(new PacketPlayerEnterSceneNotify(victim, EnterType.EnterSelf, EnterReason.TeamKick, victim.getScene().getId(), victim.getPos()));
		return true;
	}
}
