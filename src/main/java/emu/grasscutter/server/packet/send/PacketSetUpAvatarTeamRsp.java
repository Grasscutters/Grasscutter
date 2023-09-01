package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetUpAvatarTeamRspOuterClass.SetUpAvatarTeamRsp;

public class PacketSetUpAvatarTeamRsp extends BasePacket {

    public PacketSetUpAvatarTeamRsp(Player player, int teamId, TeamInfo teamInfo) {
        super(PacketOpcodes.SetUpAvatarTeamRsp);

        SetUpAvatarTeamRsp.Builder proto =
                SetUpAvatarTeamRsp.newBuilder()
                        .setTeamId(teamId)
                        .setCurAvatarGuid(player.getTeamManager().getCurrentCharacterGuid());

        for (int avatarId : teamInfo.getAvatars()) {
            proto.addAvatarTeamGuidList(player.getAvatars().getAvatarById(avatarId).getGuid());
        }

        this.setData(proto);
    }
}
