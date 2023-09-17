package emu.grasscutter.game;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.packet.send.PacketAddNoGachaAvatarCardNotify;
import emu.grasscutter.utils.objects.HandbookBody.*;
import java.util.Objects;

/** Commands executed by the handbook. */
public interface HandbookActions {
    /**
     * Checks if the player is authenticated.
     *
     * @param player The player.
     * @param token The player's unique session token.
     * @return True if the player is authenticated.
     */
    static boolean isAuthenticated(Player player, String token) {
        // Check properties.
        if (player == null || token == null) return false;
        // Compare the session key and token.
        return player.getSessionKey().equals(token);
    }

    /**
     * Grants an avatar to the player.
     *
     * @param request The request object.
     * @return The response object.
     */
    static Response grantAvatar(GrantAvatar request) {
        // Validate the request.
        if (request.getPlayer() == null || request.getAvatar() == null) {
            return Response.builder().status(400).message("Invalid request.").build();
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested avatar.
            var avatarId = Integer.parseInt(request.getAvatar());
            var avatarData = GameData.getAvatarDataMap().get(avatarId);

            // Validate the request.
            if (player == null) {
                return Response.builder().status(1).message("Player not found.").build();
            }
            if (!HandbookActions.isAuthenticated(player, request.getPlayerToken())) {
                return Response.builder().status(1).message("Player not authorized.").build();
            }
            if (avatarData == null) {
                return Response.builder().status(400).message("Invalid avatar ID.").build();
            }

            // Create the new avatar.
            var avatar = new Avatar(avatarData);
            avatar.setLevel(request.getLevel());
            avatar.setPromoteLevel(Avatar.getMinPromoteLevel(avatar.getLevel()));
            Objects.requireNonNull(avatar.getSkillDepot())
                    .getSkillsAndEnergySkill()
                    .forEach(id -> avatar.setSkillLevel(id, request.getTalentLevels()));
            avatar.forceConstellationLevel(request.getConstellations());
            avatar.recalcStats(true);
            avatar.save();

            // Add the avatar.
            player.addAvatar(avatar);
            player.sendPacket(new PacketAddNoGachaAvatarCardNotify(avatar, ActionReason.Gm));
            return Response.builder().status(200).message("Avatar granted.").build();
        } catch (NumberFormatException ignored) {
            return Response.builder().status(500).message("Invalid player UID or avatar ID.").build();
        } catch (Exception exception) {
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
            return Response.builder()
                    .status(500)
                    .message("An error occurred while granting the avatar.")
                    .build();
        }
    }

    /**
     * Gives an item to the player.
     *
     * @param request The request object.
     * @return The response object.
     */
    static Response giveItem(GiveItem request) {
        // Validate the request.
        if (request.getPlayer() == null || request.getItem() == null) {
            return Response.builder().status(400).message("Invalid request.").build();
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested item.
            var itemId = Integer.parseInt(request.getItem());
            var itemData = GameData.getItemDataMap().get(itemId);

            // Validate the request.
            if (player == null) {
                return Response.builder().status(1).message("Player not found.").build();
            }
            if (!HandbookActions.isAuthenticated(player, request.getPlayerToken())) {
                return Response.builder().status(1).message("Player not authorized.").build();
            }
            if (itemData == null) {
                return Response.builder().status(400).message("Invalid player UID or item ID.").build();
            }

            // Add the item to the player's inventory.
            var amount = request.getAmount();
            if (amount > Integer.MAX_VALUE) {
                // Calculate the amount of times we need to add the item.
                var times = Math.floor((double) amount / Integer.MAX_VALUE);
                amount = amount % Integer.MAX_VALUE;

                // Add the item the amount of times we need to.
                for (var i = 0; i < times; i++) {
                    var itemStack = new GameItem(itemData, Integer.MAX_VALUE);
                    player.getInventory().addItem(itemStack, ActionReason.Gm);
                }
            }

            // Create the item stack and add it to the player's inventory.
            var itemStack = new GameItem(itemData, (int) amount);
            player.getInventory().addItem(itemStack, ActionReason.Gm);

            return Response.builder().status(200).message("Item granted.").build();
        } catch (NumberFormatException ignored) {
            return Response.builder().status(500).message("Invalid player UID or item ID.").build();
        } catch (Exception exception) {
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
            return Response.builder()
                    .status(500)
                    .message("An error occurred while granting the item.")
                    .build();
        }
    }

    /**
     * Teleports the player to a location.
     *
     * @param request The request object.
     * @return The response object.
     */
    static Response teleportTo(TeleportTo request) {
        // Validate the request.
        if (request.getPlayer() == null || request.getScene() == null) {
            return Response.builder().status(400).message("Invalid request.").build();
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested scene.
            var sceneId = Integer.parseInt(request.getScene());

            // Validate the request.
            if (player == null) {
                return Response.builder().status(1).message("Player not found.").build();
            }
            if (!HandbookActions.isAuthenticated(player, request.getPlayerToken())) {
                return Response.builder().status(1).message("Player not authorized.").build();
            }

            // Find the scene in the player's world.
            var scene = player.getWorld().getSceneById(sceneId);
            if (scene == null) {
                return Response.builder().status(400).message("Invalid scene ID.").build();
            }

            // Resolve the correct teleport position.
            var position = scene.getDefaultLocation(player);
            var rotation = scene.getDefaultRotation(player);
            // Teleport the player.
            scene.getWorld().transferPlayerToScene(player, scene.getId(), position);
            player.getRotation().set(rotation);

            return Response.builder().status(200).message("Player teleported.").build();
        } catch (NumberFormatException ignored) {
            return Response.builder().status(400).message("Invalid player UID or scene ID.").build();
        } catch (Exception exception) {
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
            return Response.builder()
                    .status(500)
                    .message("An error occurred while teleporting to the scene.")
                    .build();
        }
    }

    /**
     * Spawns an entity(s) in the player's world.
     *
     * @param request The request object.
     * @return The response object.
     */
    static Response spawnEntity(SpawnEntity request) {
        // Validate the request.
        if (request.getPlayer() == null || request.getEntity() == null) {
            return Response.builder().status(400).message("Invalid request.").build();
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested entity.
            var entityId = Integer.parseInt(request.getEntity());
            var entityData = GameData.getMonsterDataMap().get(entityId);

            // Validate the request.
            if (player == null) {
                return Response.builder().status(1).message("Player not found.").build();
            }
            if (!HandbookActions.isAuthenticated(player, request.getPlayerToken())) {
                return Response.builder().status(1).message("Player not authorized.").build();
            }
            if (entityData == null) {
                return Response.builder().status(400).message("Invalid entity ID.").build();
            }

            // Validate request properties.
            var scene = player.getScene();
            var level = request.getLevel();
            if (scene == null || level > 200 || level < 1) {
                return Response.builder().status(400).message("Invalid scene or level.").build();
            }

            // Create the entity.
            for (var i = 1; i <= request.getAmount(); i++) {
                var entity =
                        new EntityMonster(scene, entityData, player.getPosition(), player.getRotation(), level);
                scene.addEntity(entity);
            }

            return Response.builder().status(200).message("Entity(s) spawned.").build();
        } catch (NumberFormatException ignored) {
            return Response.builder().status(400).message("Invalid player UID or entity ID.").build();
        } catch (Exception exception) {
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
            return Response.builder()
                    .status(500)
                    .message("An error occurred while teleporting to the scene.")
                    .build();
        }
    }
}
