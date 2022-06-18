package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarTeamOuterClass.AvatarTeam;
import emu.grasscutter.net.proto.AvatarTeamUpdateNotifyOuterClass.AvatarTeamUpdateNotify;

import java.util.Map.Entry;

public class PacketAvatarTeamUpdateNotify extends BasePacket {

    public PacketAvatarTeamUpdateNotify(Player player) {
        super(PacketOpcodes.AvatarTeamUpdateNotify);

        AvatarTeamUpdateNotify.Builder proto = AvatarTeamUpdateNotify.newBuilder();

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
