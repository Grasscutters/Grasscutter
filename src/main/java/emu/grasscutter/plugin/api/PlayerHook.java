package emu.grasscutter.plugin.api;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.server.packet.send.*;

/** Hooks into the {@link Player} class, adding convenient ways to do certain things. */
public interface PlayerHook {
    /**
     * @return The player that this hook is attached to.
     */
    Player getPlayer();

    /** Kicks a player from the server. TODO: Refactor to kick using a packet. */
    default void kick() {
        this.getPlayer().getSession().close();
    }

    /**
     * Sends a player to another scene.
     *
     * @param sceneId The scene to send the player to.
     */
    default void changeScenes(int sceneId) {
        this.getPlayer()
                .getWorld()
                .transferPlayerToScene(this.getPlayer(), sceneId, this.getPlayer().getPosition());
    }

    /**
     * Broadcasts an avatar property notify to all world players.
     *
     * @param property The property that was updated.
     */
    default void updateFightProperty(FightProperty property) {
        this.broadcastPacketToWorld(
                new PacketAvatarFightPropUpdateNotify(this.getCurrentAvatar(), property));
    }

    /**
     * Broadcasts the packet sent to all world players.
     *
     * @param packet The packet to send.
     */
    default void broadcastPacketToWorld(BasePacket packet) {
        this.getPlayer().getWorld().broadcastPacket(packet);
    }

    /**
     * Set the currently equipped avatar's health.
     *
     * @param health The health to set the avatar to.
     */
    default void setHealth(float health) {
        this.getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, health);
        this.updateFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
    }

    /**
     * Revives the specified avatar.
     *
     * @param avatar The avatar to revive.
     */
    default void reviveAvatar(Avatar avatar) {
        this.broadcastPacketToWorld(new PacketAvatarLifeStateChangeNotify(avatar));
    }

    /**
     * Teleports a player to a position. This will **not** transfer the player to another scene.
     *
     * @param position The position to teleport the player to.
     */
    default void teleport(Position position) {
        this.getPlayer().getPosition().set(position);
        this.getPlayer()
                .sendPacket(
                        new PacketPlayerEnterSceneNotify(
                                this.getPlayer(),
                                EnterType.ENTER_TYPE_JUMP,
                                EnterReason.TransPoint,
                                this.getPlayer().getSceneId(),
                                position));
    }

    /**
     * Gets the currently selected avatar's max health.
     *
     * @return The max health as a float.
     */
    default float getMaxHealth() {
        return this.getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
    }

    /**
     * Gets the currently selected avatar in entity form.
     *
     * @return The avatar as an {@link EntityAvatar}.
     */
    default EntityAvatar getCurrentAvatarEntity() {
        return this.getPlayer().getTeamManager().getCurrentAvatarEntity();
    }

    /**
     * Gets the currently selected avatar.
     *
     * @return The avatar as an {@link Avatar}.
     */
    default Avatar getCurrentAvatar() {
        return this.getCurrentAvatarEntity().getAvatar();
    }
}
