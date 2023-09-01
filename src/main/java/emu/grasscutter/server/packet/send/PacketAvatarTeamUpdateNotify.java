package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarTeamUpdateNotifyOuterClass.AvatarTeamUpdateNotify;

public class PacketAvatarTeamUpdateNotify extends BasePacket {

    public PacketAvatarTeamUpdateNotify(Player player) {
        super(PacketOpcodes.AvatarTeamUpdateNotify);

        AvatarTeamUpdateNotify.Builder proto = AvatarTeamUpdateNotify.newBuilder();

        var teamManager = player.getTeamManager();
        if (teamManager.isUsingTrialTeam()) {
            proto.addAllTempAvatarGuidList(
                    teamManager.getActiveTeam().stream()
                            .map(entity -> entity.getAvatar().getGuid())
                            .toList());
        } else {
            teamManager
                    .getTeams()
                    .forEach((key, value) -> proto.putAvatarTeamMap(key, value.toProto(player)));
        }

        this.setData(proto);
    }

    /** Used for locking/unlocking team modification. */
    public PacketAvatarTeamUpdateNotify() {
        super(PacketOpcodes.AvatarTeamUpdateNotify);

        this.setData(AvatarTeamUpdateNotify.newBuilder().build());
    }
}
