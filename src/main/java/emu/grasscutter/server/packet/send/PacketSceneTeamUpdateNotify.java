package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.SceneTeamAvatarOuterClass.SceneTeamAvatar;
import emu.grasscutter.net.proto.SceneTeamUpdateNotifyOuterClass.SceneTeamUpdateNotify;

public class PacketSceneTeamUpdateNotify extends BasePacket {

    public PacketSceneTeamUpdateNotify(Player player) {
        super(PacketOpcodes.SceneTeamUpdateNotify);

        SceneTeamUpdateNotify.Builder proto = SceneTeamUpdateNotify.newBuilder()
            .setIsInMp(player.getWorld().isMultiplayer());

        for (Player p : player.getWorld().getPlayers()) {
            for (EntityAvatar entityAvatar : p.getTeamManager().getActiveTeam()) {
                SceneTeamAvatar.Builder avatarProto = SceneTeamAvatar.newBuilder()
                    .setPlayerUid(p.getUid())
                    .setAvatarGuid(entityAvatar.getAvatar().getGuid())
                    .setSceneId(p.getSceneId())
                    .setEntityId(entityAvatar.getId())
                    .setSceneEntityInfo(entityAvatar.toProto())
                    .setWeaponGuid(entityAvatar.getAvatar().getWeapon().getGuid())
                    .setWeaponEntityId(entityAvatar.getWeaponEntityId())
                    .setIsPlayerCurAvatar(p.getTeamManager().getCurrentAvatarEntity() == entityAvatar)
                    .setIsOnScene(p.getTeamManager().getCurrentAvatarEntity() == entityAvatar)
                    .setAvatarAbilityInfo(AbilitySyncStateInfo.newBuilder())
                    .setWeaponAbilityInfo(AbilitySyncStateInfo.newBuilder())
                    .setAbilityControlBlock(entityAvatar.getAbilityControlBlock());

                if (player.getWorld().isMultiplayer()) {
                    avatarProto.setAvatarInfo(entityAvatar.getAvatar().toProto());
                    avatarProto.setSceneAvatarInfo(entityAvatar.getSceneAvatarInfo()); // why mihoyo...
                }

                proto.addSceneTeamAvatarList(avatarProto);
            }
        }

        this.setData(proto);
    }
}
