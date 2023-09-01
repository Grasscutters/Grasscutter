package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.SyncTeamEntityNotifyOuterClass.SyncTeamEntityNotify;
import emu.grasscutter.net.proto.TeamEntityInfoOuterClass.TeamEntityInfo;

public class PacketSyncTeamEntityNotify extends BasePacket {

    public PacketSyncTeamEntityNotify(Player player) {
        super(PacketOpcodes.SyncTeamEntityNotify);

        SyncTeamEntityNotify.Builder proto =
                SyncTeamEntityNotify.newBuilder().setSceneId(player.getSceneId());

        if (player.getWorld().isMultiplayer()) {
            for (var p : player.getWorld()) {
                // Skip if same player
                if (player == p) {
                    continue;
                }

                // Set info
                TeamEntityInfo info =
                        TeamEntityInfo.newBuilder()
                                .setTeamEntityId(p.getTeamManager().getEntity().getId())
                                .setAuthorityPeerId(p.getPeerId())
                                .setTeamAbilityInfo(AbilitySyncStateInfo.newBuilder())
                                .build();

                proto.addTeamEntityInfoList(info);
            }
        }

        this.setData(proto);
    }
}
