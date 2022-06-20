package emu.grasscutter.plugin.api;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.server.packet.send.PacketAvatarFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketAvatarLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.utils.Position;

/**
 * Hooks into the {@link Player} class, adding convenient ways to do certain things.
 */
public final class PlayerHook {
    private final Player player;

    /**
     * Hooks into the player.
     *
     * @param player The player to hook into.
     */
    public PlayerHook(Player player) {
        this.player = player;
    }

    /**
     * Kicks a player from the server.
     * TODO: Refactor to kick using a packet.
     */
    public void kick() {
        this.player.getSession().close();
    }

    /**
     * Sends a player to another scene.
     *
     * @param sceneId The scene to send the player to.
     */
    public void changeScenes(int sceneId) {
        this.player.getWorld().transferPlayerToScene(this.player, sceneId, this.player.getPos());
    }

    /**
     * Broadcasts an avatar property notify to all world players.
     *
     * @param property The property that was updated.
     */
    public void updateFightProperty(FightProperty property) {
        this.broadcastPacketToWorld(new PacketAvatarFightPropUpdateNotify(this.getCurrentAvatar(), property));
    }

    /**
     * Broadcasts the packet sent to all world players.
     *
     * @param packet The packet to send.
     */
    public void broadcastPacketToWorld(BasePacket packet) {
        this.player.getWorld().broadcastPacket(packet);
    }

    /**
     * Set the currently equipped avatar's health.
     *
     * @param health The health to set the avatar to.
     */
    public void setHealth(float health) {
        this.getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, health);
        this.updateFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
    }

    /**
     * Revives the specified avatar.
     *
     * @param avatar The avatar to revive.
     */
    public void reviveAvatar(Avatar avatar) {
        this.broadcastPacketToWorld(new PacketAvatarLifeStateChangeNotify(avatar));
    }

    /**
     * Teleports a player to a position.
     * This will **not** transfer the player to another scene.
     *
     * @param position The position to teleport the player to.
     */
    public void teleport(Position position) {
        this.player.getPos().set(position);
        this.player.sendPacket(new PacketPlayerEnterSceneNotify(this.player,
            EnterType.ENTER_TYPE_JUMP, EnterReason.TransPoint,
            this.player.getSceneId(), position
        ));
    }

    /**
     * Gets the currently selected avatar's max health.
     *
     * @return The max health as a float.
     */
    public float getMaxHealth() {
        return this.getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
    }

    /**
     * Gets the currently selected avatar in entity form.
     *
     * @return The avatar as an {@link EntityAvatar}.
     */
    public EntityAvatar getCurrentAvatarEntity() {
        return this.player.getTeamManager().getCurrentAvatarEntity();
    }

    /**
     * Gets the currently selected avatar.
     *
     * @return The avatar as an {@link Avatar}.
     */
    public Avatar getCurrentAvatar() {
        return this.getCurrentAvatarEntity().getAvatar();
    }
}