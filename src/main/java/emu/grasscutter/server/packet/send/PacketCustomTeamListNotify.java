package emu.grasscutter.server.packet.send;

import java.util.Map.Entry;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarTeamOuterClass.AvatarTeam;
import emu.grasscutter.net.proto.CustomTeamListNotifyOuterClass.CustomTeamListNotify;

public class PacketCustomTeamListNotify extends BasePacket {
    public PacketCustomTeamListNotify(Player player) {
        super(PacketOpcodes.CustomTeamListNotify);

        CustomTeamListNotify.Builder proto = CustomTeamListNotify.newBuilder();

        // Add the id list for custom teams.
        for (int id : player.getTeamManager().getTeams().keySet()) {
            if (id > 4) {
                proto.addCustomTeamIds(id);
            }
        }

        // Add the avatar lists for all the teams the player has.
        for (Entry<Integer, TeamInfo> entry : player.getTeamManager().getTeams().entrySet()) {
            TeamInfo teamInfo = entry.getValue();
            
            AvatarTeam.Builder avatarTeam = AvatarTeam.newBuilder()
                .setTeamName(teamInfo.getName());

            for (int i = 0; i < teamInfo.getAvatars().size(); i++) {
                Avatar avatar = player.getAvatars().getAvatarById(teamInfo.getAvatars().get(i));
                avatarTeam.addAvatarGuidList(avatar.getGuid());
            }

            proto.putAvatarTeamMap(entry.getKey(), avatarTeam.build());
        }
    
        this.setData(proto);
    }
}
