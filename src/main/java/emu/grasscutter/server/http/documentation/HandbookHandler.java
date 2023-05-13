package emu.grasscutter.server.http.documentation;

import static emu.grasscutter.config.Configuration.HANDBOOK;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.utils.FileUtils;
import emu.grasscutter.utils.objects.HandbookBody;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Objects;

/** Handles requests for the new GM Handbook. */
public final class HandbookHandler implements Router {
    private final byte[] handbook;
    private final boolean serve;

    /**
     * Constructor for the handbook router. Enables serving the handbook if the handbook file is
     * found.
     */
    public HandbookHandler() {
        this.handbook = FileUtils.readResource("/handbook.html");
        this.serve = HANDBOOK.enable && this.handbook.length > 0;
    }

    @Override
    public void applyRoutes(Javalin javalin) {
        if (!this.serve) return;

        // The handbook content. (built from src/handbook)
        javalin.get("/handbook", this::serveHandbook);

        // Handbook control routes.
        javalin.post("/handbook/avatar", this::grantAvatar);
        javalin.post("/handbook/item", this::giveItem);
        javalin.post("/handbook/teleport", this::teleportTo);
    }

    /**
     * @return True if the server can execute handbook commands.
     */
    private boolean controlSupported() {
        return HANDBOOK.enable && Grasscutter.getRunMode() == ServerRunMode.HYBRID;
    }

    /**
     * Serves the handbook if it is found.
     *
     * @route GET /handbook
     * @param ctx The Javalin request context.
     */
    private void serveHandbook(Context ctx) {
        if (!this.serve) {
            ctx.status(500).result("Handbook not found.");
        } else {
            ctx.contentType("text/html").result(this.handbook);
        }
    }

    /**
     * Grants the avatar to the user.
     *
     * @route POST /handbook/avatar
     * @param ctx The Javalin request context.
     */
    private void grantAvatar(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.GrantAvatar.class);
        // Validate the request.
        if (request.getPlayer() == null || request.getAvatar() == null) {
            ctx.status(400).result("Invalid request.");
            return;
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested avatar.
            var avatarId = Integer.parseInt(request.getAvatar());
            var avatarData = GameData.getAvatarDataMap().get(avatarId);

            // Validate the request.
            if (player == null || avatarData == null) {
                ctx.status(400).result("Invalid player UID or avatar ID.");
                return;
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

            player.addAvatar(avatar); // Add the avatar.
            ctx.json(HandbookBody.Response.builder().status(200).message("Avatar granted.").build());
        } catch (NumberFormatException ignored) {
            ctx.status(500).result("Invalid player UID or avatar ID.");
        } catch (Exception exception) {
            ctx.status(500).result("An error occurred while granting the avatar.");
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
        }
    }

    /**
     * Gives an item to the user.
     *
     * @route POST /handbook/item
     * @param ctx The Javalin request context.
     */
    private void giveItem(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.GiveItem.class);
        // Validate the request.
        if (request.getPlayer() == null || request.getItem() == null) {
            ctx.status(400).result("Invalid request.");
            return;
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested item.
            var itemId = Integer.parseInt(request.getItem());
            var itemData = GameData.getItemDataMap().get(itemId);

            // Validate the request.
            if (player == null || itemData == null) {
                ctx.status(400).result("Invalid player UID or item ID.");
                return;
            }

            // Create the new item stack.
            var itemStack = new GameItem(itemData, request.getAmount());
            // Add the item to the inventory.
            player.getInventory().addItem(itemStack, ActionReason.Gm);

            ctx.json(HandbookBody.Response.builder().status(200).message("Item granted.").build());
        } catch (NumberFormatException ignored) {
            ctx.status(500).result("Invalid player UID or item ID.");
        } catch (Exception exception) {
            ctx.status(500).result("An error occurred while granting the item.");
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
        }
    }

    /**
     * Teleports the user to a location.
     *
     * @route POST /handbook/teleport
     * @param ctx The Javalin request context.
     */
    private void teleportTo(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.TeleportTo.class);
        // Validate the request.
        if (request.getPlayer() == null || request.getScene() == null) {
            ctx.status(400).result("Invalid request.");
            return;
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested scene.
            var sceneId = Integer.parseInt(request.getScene());

            // Validate the request.
            if (player == null) {
                ctx.status(400).result("Invalid player UID.");
                return;
            }

            // Find the scene in the player's world.
            var scene = player.getWorld().getSceneById(sceneId);
            if (scene == null) {
                ctx.status(400).result("Invalid scene ID.");
                return;
            }

            // Resolve the correct teleport position.
            var position = scene.getDefaultLocation(player);
            var rotation = scene.getDefaultRotation(player);
            // Teleport the player.
            scene.getWorld().transferPlayerToScene(player, scene.getId(), position);
            player.getRotation().set(rotation);

            ctx.json(HandbookBody.Response.builder().status(200).message("Player teleported.").build());
        } catch (NumberFormatException ignored) {
            ctx.status(400).result("Invalid player UID or scene ID.");
        } catch (Exception exception) {
            ctx.status(500).result("An error occurred while teleporting to the scene.");
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
        }
    }

    /**
     * Spawns an entity in the world.
     *
     * @route POST /handbook/spawn
     * @param ctx The Javalin request context.
     */
    private void spawnEntity(Context ctx) {
        if (!this.controlSupported()) {
            ctx.status(500).result("Handbook control not supported.");
            return;
        }

        // Parse the request body into a class.
        var request = ctx.bodyAsClass(HandbookBody.SpawnEntity.class);
        // Validate the request.
        if (request.getPlayer() == null || request.getEntity() == null) {
            ctx.status(400).result("Invalid request.");
            return;
        }

        try {
            // Parse the requested player.
            var playerId = Integer.parseInt(request.getPlayer());
            var player = Grasscutter.getGameServer().getPlayerByUid(playerId);

            // Parse the requested entity.
            var entityId = Integer.parseInt(request.getEntity());
            var entityData = GameData.getMonsterDataMap().get(entityId);

            // Validate the request.
            if (player == null || entityData == null) {
                ctx.status(400).result("Invalid player UID or entity ID.");
                return;
            }

            // Validate request properties.
            var scene = player.getScene();
            var level = request.getLevel();
            if (scene == null || level > 200 || level < 1) {
                ctx.status(400).result("Invalid scene or level.");
                return;
            }

            // Create the entity.
            for (var i = 1; i <= request.getAmount(); i++) {
                var entity = new EntityMonster(scene, entityData, player.getPosition(), level);
                scene.addEntity(entity);
            }

            ctx.json(HandbookBody.Response.builder().status(200).message("Entity(s) spawned.").build());
        } catch (NumberFormatException ignored) {
            ctx.status(400).result("Invalid player UID or entity ID.");
        } catch (Exception exception) {
            ctx.status(500).result("An error occurred while teleporting to the scene.");
            Grasscutter.getLogger().debug("A handbook command error occurred.", exception);
        }
    }
}
