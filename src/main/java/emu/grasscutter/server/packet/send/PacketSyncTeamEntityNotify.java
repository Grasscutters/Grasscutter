package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AbilitySyncStateInfoOuterClass.AbilitySyncStateInfo;
import emu.grasscutter.net.proto.SyncTeamEntityNotifyOuterClass.SyncTeamEntityNotify;
import emu.grasscutter.net.proto.TeamEntityInfoOuterClass.TeamEntityInfo;

public class PacketSyncTeamEntityNotify extends BasePacket {

    public PacketSyncTeamEntityNotify(Player player) {
        super(PacketOpcodes.SyncTeamEntityNotify);

        SyncTeamEntityNotify.Builder proto = SyncTeamEntityNotify.newBuilder()
            .setSceneId(player.getSceneId());

        if (player.getWorld().isMultiplayer()) {
            for (Player p : player.getWorld().getPlayers()) {
                // Skip if same player
                if (player == p) {
                    continue;
                }

                // Set info
                TeamEntityInfo info = TeamEntityInfo.newBuilder()
                    .setTeamEntityId(p.getTeamManager().getEntityId())
                    .setAuthorityPeerId(p.getPeerId())
                    .setTeamAbilityInfo(AbilitySyncStateInfo.newBuilder())
                    .build();

                proto.addTeamEntityInfoList(info);
            }
        }

        this.setData(proto);
    }
}
