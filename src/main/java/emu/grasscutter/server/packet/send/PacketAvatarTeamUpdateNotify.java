package emu.grasscutter.server.packet.send;

import java.util.Map.Entry;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarTeamOuterClass.AvatarTeam;
import emu.grasscutter.net.proto.AvatarTeamUpdateNotifyOuterClass.AvatarTeamUpdateNotify;

public class PacketAvatarTeamUpdateNotify extends BasePacket {

    public PacketAvatarTeamUpdateNotify(Player player) {
        super(PacketOpcodes.AvatarTeamUpdateNotify);

        AvatarTeamUpdateNotify.Builder proto = AvatarTeamUpdateNotify.newBuilder();

        for (Entry<Integer, TeamInfo> entry : player.getTeamManager().getTeams().entrySet()) {
            TeamInfo teamInfo = entry.getValue();
            proto.putAvatarTeamMap(entry.getKey(), teamInfo.toProto(player));
        }

        this.setData(proto);
    }
}
