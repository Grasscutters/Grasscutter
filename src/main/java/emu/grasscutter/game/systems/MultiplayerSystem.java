package emu.grasscutter.game.systems;

import emu.grasscutter.game.CoopRequest;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.Player.SceneLoadState;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.net.proto.PlayerApplyEnterMpResultNotifyOuterClass;
import emu.grasscutter.server.game.*;
import emu.grasscutter.server.packet.send.*;

public class MultiplayerSystem extends BaseGameSystem {

    public MultiplayerSystem(GameServer server) {
        super(server);
    }

    public void applyEnterMp(Player player, int targetUid) {
        Player target = getServer().getPlayerByUid(targetUid);
        if (target == null) {
            player.sendPacket(new PacketPlayerApplyEnterMpResultNotify(targetUid, "", false, PlayerApplyEnterMpResultNotifyOuterClass.PlayerApplyEnterMpResultNotify.Reason.REASON_PLAYER_CANNOT_ENTER_MP));
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

    public void applyEnterMpReply(Player hostPlayer, int applyUid, boolean isAgreed) {
        // Checks
        CoopRequest request = hostPlayer.getCoopRequests().get(applyUid);
        if (request == null || request.isExpired()) {
            return;
        }

        // Remove now that we are handling it
        Player requester = request.getRequester();
        hostPlayer.getCoopRequests().remove(applyUid);

        // Sanity checks - Dont let the requesting player join if they are already in multiplayer
        if (requester.getWorld().isMultiplayer()) {
            request.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(hostPlayer, false, PlayerApplyEnterMpResultNotifyOuterClass.PlayerApplyEnterMpResultNotify.Reason.REASON_PLAYER_CANNOT_ENTER_MP));
            return;
        }

        // Response packet
        request.getRequester().sendPacket(new PacketPlayerApplyEnterMpResultNotify(hostPlayer, isAgreed, PlayerApplyEnterMpResultNotifyOuterClass.PlayerApplyEnterMpResultNotify.Reason.REASON_PLAYER_JUDGE));

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
            hostPlayer.sendPacket(new PacketPlayerEnterSceneNotify(hostPlayer, hostPlayer, EnterType.ENTER_TYPE_SELF, EnterReason.HostFromSingleToMp, hostPlayer.getScene().getId(), hostPlayer.getPosition()));
        }

        // Set scene pos and id of requester to the host player's
        requester.getPosition().set(hostPlayer.getPosition());
        requester.getRotation().set(hostPlayer.getRotation());
        requester.setSceneId(hostPlayer.getSceneId());

        // Make requester join
        hostPlayer.getWorld().addPlayer(requester);

        // Packet
        requester.sendPacket(new PacketPlayerEnterSceneNotify(requester, hostPlayer, EnterType.ENTER_TYPE_OTHER, EnterReason.TeamJoin, hostPlayer.getScene().getId(), hostPlayer.getPosition()));
    }

    public boolean leaveCoop(Player player) {
        // Make sure player is not in home
        if (player.getCurHomeWorld().isInHome(player)) {
            return false;
        }

        // Make sure player's world is multiplayer
        if (!player.getWorld().isMultiplayer()) {
            return false;
        }

        // Make sure everyone's scene is loaded
        for (Player p : player.getWorld().getPlayers()) {
            if (p.getSceneLoadState() != SceneLoadState.LOADED) {
                return false;
            }
        }

        // Create new world for player
        World world = new World(player);
        world.addPlayer(player);

        // Packet
        player.sendPacket(new PacketPlayerEnterSceneNotify(player, EnterType.ENTER_TYPE_SELF, EnterReason.TeamBack, player.getScene().getId(), player.getPosition()));

        return true;
    }

    public boolean kickPlayer(Player player, int targetUid) {
        // Make sure player's world is multiplayer and that player is owner
        if (!player.getWorld().isMultiplayer() || player.getWorld().getHost() != player) {
            return false;
        }

        // Get victim and sanity checks
        Player victim = player.getServer().getPlayerByUid(targetUid);

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

        victim.sendPacket(new PacketPlayerEnterSceneNotify(victim, EnterType.ENTER_TYPE_SELF, EnterReason.TeamKick, victim.getScene().getId(), victim.getPosition()));
        return true;
    }
}
